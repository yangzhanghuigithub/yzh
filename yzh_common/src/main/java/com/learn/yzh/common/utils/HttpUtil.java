package com.learn.yzh.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http访问工具类
 *
 */
public class HttpUtil {
private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final String METHOD_POST = "POST";

	private static final String METHOD_GET = "GET";

	private static final int CONNECTTIMEOUT = 5000;

	private static final int READTIMEOUT = 5000;

	// 证书管理
	private static class DefaultTrustManager implements X509TrustManager {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] cert, String oauthType)
				throws java.security.cert.CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] cert, String oauthType)
				throws java.security.cert.CertificateException {
		}
	}

	/**
	 * 创建连接
	 * @param url
	 * @param method
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection(URL url, String method, String contentType) throws IOException {
		HttpURLConnection conn = null;
		// 判断连接协议
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx = null;
			try {
				// 用TLS安全传输层协议
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					// 默认都认证通过
					return true;
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		conn.setRequestMethod(method);
		conn.setDoInput(true); // 允许输入
		conn.setDoOutput(true);// 允许输出

		conn.setRequestProperty("Content-Type", contentType);
		conn.setRequestProperty("Connection", "Keep-Alive"); // 设置连接持续有效
		return conn;

	}

	/**
	 * 通过get访问 默认编码设置为UTF-8
	 * @param url 连接地址
	 * @param params 参数
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params) throws IOException {
		return doGet(url, params, DEFAULT_CHARSET);
	}

	public static String doGet(String url, Map<String, String> params, boolean encode) throws IOException {
		return doGet(url, params, DEFAULT_CHARSET, encode);
	}

	/**
	 * 提交Body数据
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String postBody(String url, String data) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpEntity entity = new StringEntity(data);
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		return EntityUtils.toString(response.getEntity());
	}
/**
 * 支持GZip的http请求
 */
	public static String payGetGZip(String url, Map<String, String> params) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		HttpGet get = new HttpGet(url);
		try {
			get.addHeader("Accept", "text/html");
			get.addHeader("Accept-Charset", "UTF-8");
			get.addHeader("Accept-Encoding", "gzip");
			get.addHeader("Accept-Language", "en-US,en");
			get.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");
			if (params != null && params.size() > 0) {
				for (String key : params.keySet()) {
					get.addHeader(key, params.get(key));
				}
			}
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			Header header = entity.getContentEncoding();
			if (header != null) {
				HeaderElement[] codecs = header.getElements();
				for (int i = 0; i < codecs.length; i++) {
//					if (codecs[i].getName().equalsIgnoreCase("gzip")) {
						response.setEntity(new GzipDecompressingEntity(entity));
//					}
				}
			}
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (get!=null) {
				get.releaseConnection();
				get = null;
			}
		}
		return null;
	}
	
	public static String payGet(String url, Map<String, String> params) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		HttpGet get = new HttpGet(url);
		try {
//			get.addHeader("Accept", "text/html");
//			get.addHeader("Accept-Charset", "UTF-8");
//			get.addHeader("Accept-Encoding", "gzip");
//			get.addHeader("Accept-Language", "en-US,en");
//			get.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");
			if (params != null && params.size() > 0) {
				for (String key : params.keySet()) {
					get.addHeader(key, params.get(key));
				}
			}
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			Header header = entity.getContentEncoding();
			if (header != null) {
				HeaderElement[] codecs = header.getElements();
				for (int i = 0; i < codecs.length; i++) {
//					if (codecs[i].getName().equalsIgnoreCase("gzip")) {
						response.setEntity(new GzipDecompressingEntity(entity));
//					}
				}
			}
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (get!=null) {
				get.releaseConnection();
				get = null;
			}
		}
		return null;
	}

	/**
	 * 通过get请求 指定编码
	 * @param url 连接地址
	 * @param params 参数
	 * @param charset 编码
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params, String charset) throws IOException {

		logger.info("确定连接地址不能为空：URL="+url+" , charset="+charset+" , params="+JSONObject.toJSONString(params));

		if (StringUtils.isEmpty(url) || params == null) {
			return null;
		}
		String response = "";
		String paramUrl = buildQuery(params, charset);
		if (StringUtils.isNotBlank(paramUrl))
			url += "?" + paramUrl;
		HttpURLConnection conn = null;
		String ctype = "text/html;charset=" + charset;
		conn = getConnection(new URL(url), METHOD_GET, ctype);
		response = getResponseAsString(conn);
		logger.info("doGet01.response:"+response);
		return response;
	}

	/**
	 * 通过get请求 指定编码
	 * @param url
	 * @param params
	 * @param contentType
	 * @param charset
	 * @param encode 是否需要encode编码
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params, String charset, boolean encode)
			throws IOException {
		// 确定连接地址不能为空
		if (StringUtils.isEmpty(url) || params == null) {
			return null;
		}
		String response = "";
		String paramUrl = buildQuery(params, charset, encode);
		if (StringUtils.isNotBlank(paramUrl))
			url += "?" + paramUrl;
		HttpURLConnection conn = null;
		String ctype = "text/html;charset=" + charset;
		conn = getConnection(new URL(url), METHOD_GET, ctype);
		response = getResponseAsString(conn);
		logger.info("doGet02.response:"+response);
		return response;
	}

	/**
	 * 通过post方法请求 默认的连接超时时间为5000ms 默认的读取超时时间为5000ms
	 * @param url 请求的地址
	 * @param params 参数
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params) throws IOException {
		return doPost(url, params, CONNECTTIMEOUT, READTIMEOUT);
	}

	/**
	 * 通过post方法请求数据，默认字符编码为utf-8
	 * @param url 请求的url地址
	 * @param params 请求的参数
	 * @param connectTimeOut 请求连接过期时间
	 * @param readTimeOut 请求读取过期时间
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params, int connectTimeOut, int readTimeOut)
			throws IOException {
		return doPost(url, params, DEFAULT_CHARSET, connectTimeOut, readTimeOut);
	}

	/**
	 * 
	 * 通过post方法请求数据
	 * @param url 请求的url地址
	 * @param params 请求的参数
	 * @param charset 字符编码格式
	 * @param connectTimeOut 请求连接过期时间
	 * @param readTimeOut 请求读取过期时间
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params, String charset, int connectTimeOut,
			int readTimeOut) throws IOException {
		HttpURLConnection conn = null;
		String response = "";
		String ctype = "text/html;charset=" + charset;
		conn = getConnection(new URL(url), METHOD_POST, ctype);
		conn.setConnectTimeout(connectTimeOut);
		conn.setReadTimeout(readTimeOut);
		conn.getOutputStream().write(buildQuery(params, charset).getBytes(charset));
		response = getResponseAsString(conn);
		logger.info("doPost.response:"+response);
		return response;
	}

	/**
	 * 处理请求参数
	 * @param params 请求参数
	 * @return 构建query
	 */
	public static String buildQuery(Map<String, String> params, String charset) {
		if (params == null || params.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			} else {
				if (sb.length() > 0) {
					sb.append("&");
				}
			}
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.areNotEmpty(key, value)) {
				try {
					sb.append(key).append("=").append(URLEncoder.encode(value, charset));
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return sb.toString();

	}

	/**
	 * 处理请求参数
	 * @param params 参数
	 * @param charset 格式
	 * @param encode 是否需要encode编码
	 * @return
	 */
	public static String buildQuery(Map<String, String> params, String charset, boolean encode) {
		if (params == null || params.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			} else {
				if (sb.length() > 0) {
					sb.append("&");
				}
			}
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.areNotEmpty(key, value)) {
				try {
					if (encode) {
						sb.append(key).append("=").append(URLEncoder.encode(value, charset));
					} else {
						sb.append(key).append("=").append(value);
					}
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return sb.toString();

	}

	private static String getResponseAsString(HttpURLConnection conn) throws IOException {
		// 获取连接的编码格式
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		try {
		// 判断连接是否失败
		if (es != null) {
			// 抛出错误异常
			String msg = getStreamAsString(es, charset);
			if (StringUtils.isEmpty(msg)) {
				throw new IOException(conn.getResponseCode() + " : " + conn.getResponseMessage());
			} else {
				throw new IOException(msg);
			}
		} else {
			// 返回连接成功的数据信息
			return getStreamAsString(conn.getInputStream(), charset);
		}
		}catch (Exception e){
			e.printStackTrace();
			if (es!=null) {
				es.close();
			}
		}finally {
			if (es!=null) {
				es.close();
			}
		}
		return null;
	}

	/**
	 * 把流转换为字符串
	 * @param input
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	private static String getStreamAsString(InputStream input, String charset) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new InputStreamReader(input, charset));
			String str;
			while ((str = bf.readLine()) != null) {
				sb.append(str);
			}
			logger.info("把流转换为字符串："+sb.toString());
			return sb.toString();
		} finally {
			if (bf != null) {
				bf.close();
				bf = null;
			}
		}

	}

	/**
	 * 获取字符串的编码
	 * @param ctype
	 * @return
	 */
	private static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;
		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split("\\;");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("\\=");
					if (pair.length == 2) {
						charset = pair[1].trim();
					}
				}
			}
		}
		return charset;
	}

}
