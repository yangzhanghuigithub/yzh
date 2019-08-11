package com.learn.yzh.common.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hello on 2017/2/17.
 */
public class DXHttpUtil {
    public static String payGetGZip(String url, Map<String, String> params) {
        DefaultHttpClient e = new DefaultHttpClient();
        HttpResponse response = null;

        HttpGet get = new HttpGet(url);
        try {
//            e.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
//            e.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
            get.addHeader("Accept", "text/html");
            get.addHeader("Accept-Charset", "UTF-8");
            get.addHeader("Accept-Encoding", "gzip");
            get.addHeader("Accept-Language", "en-US,en");
            get.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");
            if(params != null && params.size() > 0) {
                Iterator entity = params.keySet().iterator();

                while(entity.hasNext()) {
                    String header = (String)entity.next();
                    get.addHeader(header, (String)params.get(header));
                }
            }

            response = e.execute(get);
            HttpEntity var12 = response.getEntity();
            Header var13 = var12.getContentEncoding();
            if(var13 != null) {
                HeaderElement[] codecs = var13.getElements();

                for(int i = 0; i < codecs.length; ++i) {
                    response.setEntity(new GzipDecompressingEntity(var12));
                }
            }
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            return result;
        } catch (ClientProtocolException var9) {
            var9.printStackTrace();
        } catch (ParseException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        }finally {
            get.releaseConnection();
            e.close();
        }
        return null;
    }

    public static void main(String[] args) {
       String a =  payGetGZip("http://api.open.yinghezhong.com/cinema/config?cid=20003927&format=json&pid=11074&_sig=f7bdb2eb00a434ecad8c2e4f5fae6dab",null);
        System.out.println(a);
    }
}
