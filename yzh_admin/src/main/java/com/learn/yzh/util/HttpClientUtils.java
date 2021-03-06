package com.learn.yzh.util;

import com.learn.yzh.common.Constants;
import com.learn.yzh.common.UrlConstants;
import com.learn.yzh.common.mapper.JsonMapper;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
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
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * httpClient工具类封装
 * Created by wl on 2017/3/31.
 */
public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private final static int CONNECT_TIMEOUT = 10*1000;// 设置请求超时5秒钟 根据业务调整
    private final static int SOCKET_TIMEOUT = 15*1000;//设置等待数据超时时间10秒钟 根据业务调整
    private final static int REQUESTCONNECT_TIMEOUT = 10*1000;// 获取请求超时毫秒
    private final static int CONNECT_TOTAL =200;// 最大连接数
  //  private final static int CONNECT_ROUTE = 20;// 每个路由基础的连接数
    private final static String ENCODE_CHARSET = "utf-8";// 响应报文解码字符集
    private final static String RESP_CONTENT = "通信失败";
    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpClient = null;

    /**
     *  httpClient 配置
     * @return  httpclient
     */
    public static  synchronized  CloseableHttpClient getHttpClient(){
            if(httpClient==null) {
                ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
                LayeredConnectionSocketFactory sslsf = createSSLConnSocketFactory();
                Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("http", plainsf).register("https", sslsf).build();
                //DNS解析器
                DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
                connManager = new PoolingHttpClientConnectionManager(registry,dnsResolver);
                // 设置socket超时时间
                SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoTimeout(SOCKET_TIMEOUT ).build();
                connManager.setDefaultSocketConfig(socketConfig);

                RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT )
                        .setConnectTimeout(CONNECT_TIMEOUT ).setSocketTimeout(SOCKET_TIMEOUT ).build();
                //
                connManager.setMaxTotal(CONNECT_TOTAL );//设置整个连接池最大连接数 根据自己的场景决定
                //是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
                connManager.setDefaultMaxPerRoute(connManager.getMaxTotal());//（目前只有一个路由，因此让他等于最大值）
                // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
                //设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)，路由是对maxTotal的细分。
                 connManager.setValidateAfterInactivity(10*1000);
               /* HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
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
                };*/
                httpClient = HttpClients.custom().setConnectionManager(connManager)
                        .setDefaultRequestConfig(requestConfig)
                        .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)//长连接配置，既获取长连接多长时间
                        .setConnectionManagerShared(false)//连接池不是共享模式
                        .evictIdleConnections(60, TimeUnit.SECONDS)
                        .evictExpiredConnections()//定期回收连接
                        .setConnectionTimeToLive(60,TimeUnit.SECONDS)
                        .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                        .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                        //设置重试次数，默认是三次，当前是禁用掉
                        .setRetryHandler(new DefaultHttpRequestRetryHandler(0,false))
                        .build();
                if (connManager != null && connManager.getTotalStats() != null) {
                    logger.info("now client pool " + connManager.getTotalStats().toString());
                }
                //JVM停止或重启时，关闭连接池释放掉连接
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    @Override
                    public  void run(){
                        releaseHttpClient();
                    }
                });
            }
        return httpClient;
    }
    /**
     * 发送HTTP_GET请求
     *
     * @see 1)该方法会自动关闭连接,释放资源
     * @see 2)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
     * @see 3)请求参数含中文时,经测试可直接传入中文,HttpClient会自动编码发给Server,应用时应根据实际效果决
    定传入前是否转码
     * @see 4)该方法会自动获取到响应消息头中[Content-Type:text/html; charset=GBK]的charset值作为响应报文的
    解码字符集
     * @see 若响应消息头中无Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1作为响应报文的解码字符集
     * @param requestURL 请求url
     * @param params 请求参数
     * @return 远程主机响应正文
     */
    public static String sendGetRequest(String reqURL,Map<String,Object> params){

        httpClient=getHttpClient();
        String respContent = RESP_CONTENT ; // 响应内容
        // reqURL = URLDecoder.decode(reqURL, ENCODE_CHARSET);
        if(reqURL.contains("http")||reqURL.contains("https")) {
            reqURL = reqURL;
        } else {
            reqURL = UrlConstants.Default_Domain + reqURL;
        }
        StringBuilder sb = new StringBuilder(reqURL);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, Object>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + (String) entry.getKey() + "=" +  entry.getValue()==null?"":entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + (String) entry.getKey() + "=" +  entry.getValue()==null?"":entry.getValue());
                }
            }
        }
        HttpGet httpget = new HttpGet(sb.toString());
        CloseableHttpResponse response = null;
        try {
            logger.info("Executing request get" + httpget.getRequestLine());
            long startTime=System.currentTimeMillis();   //获取开始时间
            response = httpClient.execute(httpget, HttpClientContext.create()); // 执行GET请求
            long endTime=System.currentTimeMillis(); //获取结束时间
            logger.info("执行GET请求耗时时间:" +
                    " " + (endTime-startTime)+"ms");
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
                Result result =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
                respContent =JsonMapper.getInstance().toJson(result);
                logger.error("【状态：" + statusLine + "】【响应头：" + Arrays.toString(allHeaders) + "】");
            }
        } catch (ConnectTimeoutException cte) {
            Result result =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            respContent =JsonMapper.getInstance().toJson(result);
            logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
        } catch (SocketTimeoutException ste) {
            Result result =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            respContent =JsonMapper.getInstance().toJson(result);
            logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
        } catch (ClientProtocolException cpe) {
            // 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
            Result result =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            respContent =JsonMapper.getInstance().toJson(result);
            logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
        } catch (ParseException pe) {
            logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
        } catch (IOException ioe) {
            Result result =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            respContent =JsonMapper.getInstance().toJson(result);
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
        return respContent;
    }

    /**
     * post 发送HTTP_POST请求
     * @param reqURL 请求url
     * @param params post传递参数
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, Map<String,Object> params) {
        return sendPostRequest(reqURL, params,"");
    }
    /**
     * 发送HTTP_POST请求 type: 默认是表单请求，
     * @see 1)该方法允许自定义任何格式和内容的HTTP请求报文体
     * @see 2)该方法会自动关闭连接,释放资源
     * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
     * @see 4)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自
    动对其转码
     * @see 5)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html; charset=GBK]的
    charset值
     * @see 若响应消息头中未指定Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1
     * @param reqURL 请求地址
     * @param reqData 请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
     * @param encodeCharset 编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
     * @return 远程主机响应正文
     */
    private static String sendPostRequest(String reqURL, Map<String,Object> params, String type){

        httpClient=getHttpClient();
        if(reqURL.contains("http")||reqURL.contains("https")) {
            reqURL = reqURL;
        } else {
            reqURL = UrlConstants.Default_Domain + reqURL;
        }
        logger.info("向SERVER端请求发送的URL:"+reqURL);
    	String result = RESP_CONTENT ;
         // 设置请求和传输超时时间

        HttpPost httpPost = new HttpPost(reqURL);
         // 这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定ONTENT_TYPE头消息
        if (type != null && type.length() > 0) {
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=" + ENCODE_CHARSET );
        } else {
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + ENCODE_CHARSET );
        }
        CloseableHttpResponse response = null;
        try {
            StringBuffer sbf = new StringBuffer();
            //添加参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (params != null && params.keySet().size() > 0) {
                boolean firstFlag = true;
                Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()==null?"":entry.getValue())));
                    if (firstFlag) {
                        sbf.append("?" + (String) entry.getKey() + "=" + entry.getValue());
                        firstFlag = false;
                    } else {
                        sbf.append("&" + (String) entry.getKey() + "=");
                        if(entry.getValue()==null) {
                            sbf.append("");
                        } else {
                            sbf.append( entry.getValue());
                        }
                    }
                }
            }
            //log.error("【请求参数为：" + sbf + "】");
            //设置表单提交编码为UTF-8
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println(reqURL+sbf.toString());
            logger.info("开始执行请求：" + reqURL+sbf.toString());
            long startTime=System.currentTimeMillis(); //获取结束时间
            response = httpClient.execute(httpPost, HttpClientContext.create());
            long endTime=System.currentTimeMillis(); //获取结束时间
            logger.info("执行post请求耗时时间： " + (endTime-startTime)+"ms");
            StatusLine statusLine = response.getStatusLine();
            Header[] allHeaders = response.getAllHeaders();
            if (statusLine.getStatusCode() == 200) {
                logger.debug(statusLine + Arrays.toString(allHeaders));
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                    logger.info("执行请求完毕：" + result);
                    EntityUtils.consume(entity);
                }
            } else {
                logger.error("【状态：" + statusLine + "】【响应头：" + Arrays.toString(allHeaders) + "】");
                Result result1 =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
                result =JsonMapper.getInstance().toJson(result1);
                //throw new HttpStatusException(statusLine, allHeaders);
            }
        } catch (ConnectTimeoutException cte) {
            Result result1 =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            result =JsonMapper.getInstance().toJson(result1);
            logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
        } catch (SocketTimeoutException ste) {
            Result result1 =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            result =JsonMapper.getInstance().toJson(result1);
            logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
        } catch (ClientProtocolException cpe) {
            Result result1 =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            result =JsonMapper.getInstance().toJson(result1);
            logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
        } catch (ParseException pe) {
            Result result1 =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            result =JsonMapper.getInstance().toJson(result1);
            logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
        } catch (IOException ioe) {
            Result result1 =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            result =JsonMapper.getInstance().toJson(result1);
            logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
        } catch (Exception ioe) {
            Result result1 =  new Result(Constants.RESULT_CODE_FAIL_STATUS,"网络异常");
            result =JsonMapper.getInstance().toJson(result1);
            logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
        }  finally {
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
    //SSL的socket工厂创建
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
       // 创建TrustManager() 用于解决javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
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
            sslContext.init(null, new TrustManager[] {(TrustManager)trustManager}, null);
            // 创建SSLSocketFactory , // 不校验域名 ,取代以前验证规则
            sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sslsf;
    }
    public static Map<HttpRoute, PoolStats> getConnManagerStats() {
        if (connManager != null) {
            Set<HttpRoute> routeSet = connManager.getRoutes();
            if (routeSet != null && !routeSet.isEmpty()) {
                Map<HttpRoute, PoolStats> routeStatsMap = new HashMap<HttpRoute, PoolStats>();
                for (HttpRoute route : routeSet) {
                    PoolStats stats = connManager.getStats(route);
                    routeStatsMap.put(route, stats);
                }
                return routeStatsMap;
            }
        }
        return null;
    }
    public static PoolStats getConnManagerTotalStats() {
        if (connManager != null) {
            return connManager.getTotalStats();
        }
        return null;
    }
    /**
     * 关闭系统时关闭httpClient
     */
    public static void releaseHttpClient() {
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("关闭httpClient异常" + e);
        } finally {
            if (connManager != null) {
                connManager.shutdown();
            }
        }
    }

    public static void main(String []args) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("memberCode","");
        map.put("movieCode","a32126ab9cde447e8c6e264a24b27f46");
        String s = null;

        Long startTime = System.currentTimeMillis();
        s = HttpClientUtils.sendGetRequest("http://101.201.210.244:8080/mplus/app/film/seats?cinemaCode=44001391&hallCode=0000000000000004&CVersion=2.6.0&OS=Android&showDate=2017-08-15&companyCode=243f1e76824946279ffe6e1849400a80&startTime=17:40&token=&eventCode=aa5238ea4cc041dcad300e67871f58a1&showCode=2624170814060B88&filmCode=001103232017&filmNo=001a03232017&memberCode=",null);
        Long endTime = System.currentTimeMillis();

        System.out.println(endTime-startTime);
    }
}
