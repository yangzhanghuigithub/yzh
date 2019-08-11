package com.learn.yzh.common.utils;

import com.netflix.ribbon.proxy.annotation.Http;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	/**
	 * POST请求
	 * 
	 * @param url
	 * @param map
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, Map<String, String> map, String charset) {
		logger.info("url:"+url+" map:"+JSONObject.fromObject(map));
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		HttpResponse response=null;
		try {
			httpClient = new SSLClient();
			// // 请求超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 300000);
			// // 读取超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 300000);
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> iterator = map.entrySet() 	.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						charset);
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(response!=null){
					response.getEntity().getContent().close();
				}
				if(httpPost!=null){
					httpPost.getEntity().getContent().close();
				}
				if(httpClient!=null){
					httpClient.getConnectionManager().shutdown();
				}
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		return result;
	}

	public static String doPost2(String url, Map<String, String> map, String charset) {
		logger.info("url:"+url+" map:"+JSONObject.fromObject(map)+" charset:"+charset);
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("fileType", map.get("fileType")));
			list.add(new BasicNameValuePair("fileSuffix", map.get("fileSuffix")));
			list.add(new BasicNameValuePair("fileData", map.get("fileData")));
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String post(String path, byte[] data) {
		try {
			HttpClient client = new SSLClient();
			HttpPost post = new HttpPost(path);
			if (data != null && data.length > 0) {
				HttpEntity entity = new ByteArrayEntity(data);
				post.setEntity(entity);
			}
			HttpResponse response = client.execute(post);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String doGet(String baseDataUrl) {
		logger.info("baseDataUrl:"+baseDataUrl);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(baseDataUrl);
			//LOGGER.info("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				//LOGGER.info("--------------------------------------");
				// 打印响应状态
				//LOGGER.info(""+ response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					//LOGGER.info("Response content length: " + entity.getContentLength());
					// 打印响应内容
					String result = EntityUtils.toString(entity);
					//LOGGER.info("Response content: " + result);
					return result;
				}
				//LOGGER.info("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

/**
 * 自定义HttpsClient
 * 
 * @author Lomis
 * 
 */
class SSLClient extends DefaultHttpClient {
	public SSLClient() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			MyX509TrustManager tm = new MyX509TrustManager();
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = this.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}

class MyX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
