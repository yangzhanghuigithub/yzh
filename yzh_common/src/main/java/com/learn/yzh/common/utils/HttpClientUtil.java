package com.learn.yzh.common.utils;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Http客户端工具类<br/>
 * 这是内部调用类，请不要在外部调用。
 * @author miklchen
 *
 */
public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";

	private final static int CONNECT_TIMEOUT = 10 * 1000;// 设置请求超时5秒钟 根据业务调整
	private final static int SOCKET_TIMEOUT = 15 * 1000;// 设置等待数据超时时间10秒钟 根据业务调整
	private final static int REQUESTCONNECT_TIMEOUT = 15000;// 获取请求超时毫秒
	private final static int CONNECT_TOTAL = 200;// 最大连接数
	// private final static int CONNECT_ROUTE = 20;// 每个路由基础的连接数
	private final static String ENCODE_CHARSET = "utf-8";// 响应报文解码字符集
	private final static String RESP_CONTENT = "通信失败";
	private final static String JSON="1";
	private final static String XML="2";

	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpClient = null;
	static {
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = createSSLConnSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", plainsf).register("https", sslsf).build();
		connManager = new PoolingHttpClientConnectionManager(registry);
		//
		connManager.setMaxTotal(CONNECT_TOTAL);// 设置整个连接池最大连接数 根据自己的场景决定
		// 是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
		connManager.setDefaultMaxPerRoute(connManager.getMaxTotal());// （目前只有一个路由，因此让他等于最大值）
		// 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
		// 设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for
		// connection from pool)，路由是对maxTotal的细分。
		//connManager.setValidateAfterInactivity(30000);
		// 设置socket超时时间
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
		connManager.setDefaultSocketConfig(socketConfig);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 3) {// 如果已经重试了3次，就放弃
					logger.error("请求通信[服务器连接请求不到，正重试。。。]");
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					logger.error("请求通信[服务器连接请求不到，正重试。。。]");
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					logger.error("请求通信[服务器连接超时，正重试。。。]");
					return true;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					logger.error("请求通信[服务器目标不可达。。。]");
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					logger.error("请求通信[服务器连接被拒绝]");
					return false;
				}
				if (exception instanceof SSLException) {// ssl握手异常
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};
		httpClient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
				.setRetryHandler(httpRequestRetryHandler).build();
		if (connManager != null && connManager.getTotalStats() != null) {
			logger.info("now client pool " + connManager.getTotalStats().toString());
		}
	}

	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		// 创建TrustManager() 用于解决javax.net.ssl.SSLPeerUnverifiedException: peer
		// not authenticated
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String authType) throws CertificateException {
				// TODO Auto-generated method stub
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String authType) throws CertificateException {
				// TODO Auto-generated method stub
			}
		};
		SSLContext sslContext;
		try {
			sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
			sslContext.init(null, new TrustManager[] { (TrustManager) trustManager }, null);
			// 创建SSLSocketFactory , // 不校验域名 ,取代以前验证规则
			sslsf = new SSLConnectionSocketFactory(sslContext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sslsf;
	}


	/**
	 * 发送HTTP_GET请求
	 *
	 * @see 1)该方法会自动关闭连接,释放资源
	 * @see 2)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
	 * @see 3)请求参数含中文时,经测试可直接传入中文,HttpClient会自动编码发给Server,应用时应根据实际效果决 定传入前是否转码
	 * @see 4)该方法会自动获取到响应消息头中[Content-Type:text/html;
	 *      charset=GBK]的charset值作为响应报文的 解码字符集
	 * @see 若响应消息头中无Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1作为响应报文的解码字符集
	 * @param requestURL
	 *            请求url
	 * @param params
	 *            请求参数
	 * @return 远程主机响应正文
	 */
	@SuppressWarnings("rawtypes")
	public static String sendGetRequest(String reqURL, Map<String, Object> params) {
		String respContent = RESP_CONTENT; // 响应内容
		StringBuffer sb = new StringBuffer();
		boolean firstFlag = true;
		if (reqURL.contains("?")) {
			firstFlag = false;
		}
		if (params != null && params.keySet().size() > 0) {
			Iterator iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry<String, Object>) iterator.next();
				try {
					if (firstFlag) {
						String string = "?" + (String) entry.getKey() + "=";
						if (entry.getValue() != null) {
							string = "?" + (String) entry.getKey() + "="
									+ URLEncoder.encode(entry.getValue().toString(), "UTF-8");
						}
						sb.append(string);
						firstFlag = false;

					} else {
						sb.append("&" + (String) entry.getKey() + "=");
						if (entry.getValue() == null) {
							sb.append("");
						} else {
							// sb.append(entry.getValue());
							sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			CloseableHttpResponse response = null;
			HttpGet httpget = null;
			try {

				httpget = new HttpGet(reqURL + sb.toString());
				httpget.setHeader(HTTP.CONTENT_TYPE, "application/xml; charset=" + ENCODE_CHARSET);
				logger.info("Executing request get" + httpget.getRequestLine());
				long startTime = System.currentTimeMillis(); // 获取开始时间
				response = httpClient.execute(httpget, HttpClientContext.create()); // 执行GET请求
				long endTime = System.currentTimeMillis(); // 获取结束时间
				logger.info("执行GET请求耗时时间:" + " " + (endTime - startTime) + "ms");
				StatusLine statusLine = response.getStatusLine();
				Header[] allHeaders = response.getAllHeaders();
				if (statusLine.getStatusCode() == 200) {
					logger.debug(statusLine + Arrays.toString(allHeaders));
					HttpEntity entity = response.getEntity(); // 获取响应实体
					if (null != entity) {
						Charset respCharset = ContentType.getOrDefault(entity).getCharset();
						respContent = EntityUtils.toString(entity, respCharset);
						EntityUtils.consume(entity);
					}
				} else {
					respContent = "网络异常get,statusCode="+statusLine.getStatusCode();
					logger.error("【状态：" + statusLine + "】【响应头：" + Arrays.toString(allHeaders) + "】");
				}
			} catch (ConnectTimeoutException cte) {
				respContent = "网络异常get,ConnectTimeoutException:"+cte.getMessage();
				logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
			} catch (SocketTimeoutException ste) {
				respContent = "网络异常get,SocketTimeoutException:"+ste.getMessage();
				logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
			} catch (ClientProtocolException cpe) {
				// 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
				respContent = "网络异常get,ClientProtocolException:"+cpe.getMessage();
				logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
			} catch (ParseException pe) {
				logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
			} catch (IOException ioe) {
				respContent = "网络异常get,IOException:"+ioe.getMessage();
				// 该异常通常是网络原因引起的,如HTTP服务器未启动等
				logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
			} finally {
				try {
					if (response != null)
						response.getEntity().getContent().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (httpget != null) {
					httpget.releaseConnection();
				}
			}
		}
		return respContent;
	}

	/**
	 * 发送HTTP_POST请求 type: 默认是表单请求，
	 *
	 * @see 1)该方法允许自定义任何格式和内容的HTTP请求报文体
	 * @see 2)该方法会自动关闭连接,释放资源
	 * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
	 * @see 4)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自 动对其转码
	 * @see 5)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html; charset=GBK]的
	 *      charset值
	 * @see 若响应消息头中未指定Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1
	 * @param reqURL
	 *            请求地址
	 * @param reqData
	 *            请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, Map<String, Object> params, String type) {
		String result = RESP_CONTENT;
		// 设置请求和传输超时时间

		HttpPost httpPost = new HttpPost(reqURL);
		// 这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定ONTENT_TYPE头消息
		if (StringUtils.equals(type,JSON)) {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=" + ENCODE_CHARSET);
		} else if (StringUtils.equals(type, XML)) {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/xml; charset=" + ENCODE_CHARSET);
		} else {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + ENCODE_CHARSET);
		}
		CloseableHttpResponse response = null;
		try {
			StringBuffer sbf = new StringBuffer();
			// 添加参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (params != null && params.keySet().size() > 0) {
				boolean firstFlag = true;
				Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, Object> entry = iterator.next();

					nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue() == null ? ""
							: entry.getValue())));
					if (firstFlag) {
						sbf.append("?" + (String) entry.getKey() + "=" + entry.getValue());
						firstFlag = false;
					} else {
						sbf.append("&" + (String) entry.getKey() + "=");
						if (entry.getValue() == null) {
							sbf.append("");
						} else {
							sbf.append(entry.getValue());
						}
					}
				}
			}
			// log.error("【请求参数为：" + sbf + "】");
			// 设置表单提交编码为UTF-8
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

			// httpPost.setEntity(new StringEntity(nvps,CHARSET));
			logger.info("开始执行请求：" + reqURL + sbf.toString());
			long startTime = System.currentTimeMillis(); // 获取结束时间

			response = httpClient.execute(httpPost, HttpClientContext.create());
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("执行post请求耗时时间： " + (endTime - startTime) + "ms");
			StatusLine statusLine = response.getStatusLine();
			Header[] allHeaders = response.getAllHeaders();
			if (statusLine.getStatusCode() == 200) {
				logger.debug(statusLine + Arrays.toString(allHeaders));
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
					// logger.info("执行请求完毕：" + result);
					EntityUtils.consume(entity);
				}
			} else {
				logger.error("【状态：" + statusLine + "】【响应头：" + Arrays.toString(allHeaders) + "】");
				result = "网络异常post,statusCode="+statusLine.getStatusCode();
				// throw new HttpStatusException(statusLine, allHeaders);
			}
		} catch (ConnectTimeoutException cte) {
			result = "网络异常post,ConnectTimeoutException:"+cte.getMessage();
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			result = "网络异常post,SocketTimeoutException:"+ste.getMessage();
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (ClientProtocolException cpe) {
			result = "网络异常post,ClientProtocolException:"+cpe.getMessage();
			logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
		} catch (ParseException pe) {
			result = "网络异常";
			logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
		} catch (IOException ioe) {
			result = "网络异常";
			logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
		} catch (Exception ioe) {
			result = "网络异常post,IOException:"+ioe.getMessage();
			logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
		} finally {
			try {
				if (response != null)
					response.getEntity().getContent().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * get HttpURLConnection
	 * @param strUrl url地址
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	public static HttpURLConnection getHttpURLConnection(String strUrl)
			throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		return httpURLConnection;
	}

	/**
	 * get HttpsURLConnection
	 * @param strUrl url地址
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	public static HttpsURLConnection getHttpsURLConnection(String strUrl)
			throws IOException {
		URL url = new URL(strUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
				.openConnection();
		return httpsURLConnection;
	}

	/**
	 * 获取不带查询串的url
	 * @param strUrl
	 * @return String
	 */
	public static String getURL(String strUrl) {

		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(0, indexOf);
			}

			return strUrl;
		}

		return strUrl;

	}

	/**
	 * 获取查询串
	 * @param strUrl
	 * @return String
	 */
	public static String getQueryString(String strUrl) {

		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(indexOf+1, strUrl.length());
			}

			return "";
		}

		return strUrl;
	}

	/**
	 * 查询字符串转换成Map<br/>
	 * name1=key1&name2=key2&...
	 * @param queryString
	 * @return
	 */
	public static Map queryString2Map(String queryString) {
		if(null == queryString || "".equals(queryString)) {
			return null;
		}

		Map m = new HashMap();
		String[] strArray = queryString.split("&");
		for(int index = 0; index < strArray.length; index++) {
			String pair = strArray[index];
			HttpClientUtil.putMapByPair(pair, m);
		}

		return m;

	}

	/**
	 * 把键值添加至Map<br/>
	 * pair:name=value
	 * @param pair name=value
	 * @param m
	 */
	public static void putMapByPair(String pair, Map m) {

		if(null == pair || "".equals(pair)) {
			return;
		}

		int indexOf = pair.indexOf("=");
		if(-1 != indexOf) {
			String k = pair.substring(0, indexOf);
			String v = pair.substring(indexOf+1, pair.length());
			if(null != k && !"".equals(k)) {
				m.put(k, v);
			}
		} else {
			m.put(pair, "");
		}
	}

	/**
	 * BufferedReader转换成String<br/>
	 * 注意:流关闭需要自行处理
	 * @param reader
	 * @return String
	 * @throws IOException
	 */
	public static String bufferedReader2String(BufferedReader reader) throws IOException {
		StringBuffer buf = new StringBuffer();
		String line = null;
		while( (line = reader.readLine()) != null) {
			buf.append(line);
			buf.append("\r\n");
		}

		return buf.toString();
	}

	/**
	 * 处理输出<br/>
	 * 注意:流关闭需要自行处理
	 * @param out
	 * @param data
	 * @param len
	 * @throws IOException
	 */
	public static void doOutput(OutputStream out, byte[] data, int len)
			throws IOException {
		int dataLen = data.length;
		int off = 0;
		while(off < dataLen) {
			if(len >= dataLen) {
				out.write(data, off, dataLen);
			} else {
				out.write(data, off, len);
			}

			//刷新缓冲区
			out.flush();

			off += len;

			dataLen -= len;
		}

	}

	/**
	 * 获取SSLContext
	 * @param trustFile
	 * @param trustPasswd
	 * @param keyFile
	 * @param keyPasswd
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	public static SSLContext getSSLContext(
			FileInputStream trustFileInputStream, String trustPasswd,
			FileInputStream keyFileInputStream, String keyPasswd)
			throws NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException, UnrecoverableKeyException,
			KeyManagementException {

		// ca
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore trustKeyStore = KeyStore.getInstance(HttpClientUtil.JKS);
		trustKeyStore.load(trustFileInputStream, HttpClientUtil
				.str2CharArray(trustPasswd));
		tmf.init(trustKeyStore);

		final char[] kp = HttpClientUtil.str2CharArray(keyPasswd);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore ks = KeyStore.getInstance(HttpClientUtil.PKCS12);
		ks.load(keyFileInputStream, kp);
		kmf.init(ks, kp);

		SecureRandom rand = new SecureRandom();
		SSLContext ctx = SSLContext.getInstance(HttpClientUtil.TLS);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

		return ctx;
	}

	/**
	 * 获取CA证书信息
	 * @param cafile CA证书文件
	 * @return Certificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static Certificate getCertificate(File cafile)
			throws CertificateException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cafile);
		Certificate cert = cf.generateCertificate(in);
		in.close();
		return cert;
	}

	/**
	 * 字符串转换成char数组
	 * @param str
	 * @return char[]
	 */
	public static char[] str2CharArray(String str) {
		if(null == str) return null;

		return str.toCharArray();
	}

	/**
	 * 存储ca证书成JKS格式
	 * @param cert
	 * @param alias
	 * @param password
	 * @param out
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static void storeCACert(Certificate cert, String alias,
			String password, OutputStream out) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance("JKS");

		ks.load(null, null);

		ks.setCertificateEntry(alias, cert);

		// store keystore
		ks.store(out, HttpClientUtil.str2CharArray(password));

	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes(Charset.forName("UTF-8")));
	}

}

