package com.learn.yzh.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learn.yzh.model.IacsUrlDataVo;
import com.learn.yzh.model.IacsWeChatDataVo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SendMsgUtils
 * @Author: shenlele
 * @Date: 2018/10/11 14:08
 * @Description:
 */
public class SendWeChatUtils {
    private CloseableHttpClient httpClient;
    private HttpPost httpPost;
    //用于提交登陆数据
    private HttpGet httpGet;
    // 用于获得登录后的页面
    public static final String CONTENT_TYPE = "Content-Type";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Gson gson = new Gson();

    /**
     * 微信授权请求，GET类型，获取授权响应，用于其他方法截取token
     *
     * @param Get_Token_Url
     * @return String 授权响应内容
     * @throws IOException
     */
    public String toAuth(String Get_Token_Url) throws IOException {
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_Token_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(" resp:{}", resp);
        return resp;
    }

    /**
     * 获取toAuth(String Get_Token_Url)返回结果中键值对中access_token键的值
     *
     * @param corpid 应用组织编号   corpsecret 应用秘钥
     */
    public String getToken(String corpid, String corpsecret) throws IOException {
        SendWeChatUtils sw = new SendWeChatUtils();
        IacsUrlDataVo uData = new IacsUrlDataVo();
        uData.setGet_Token_Url(corpid, corpsecret);
        String resp = sw.toAuth(uData.getGet_Token_Url());
        Map<String, Object> map = gson.fromJson(resp, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map.get("access_token").toString();
    }

    /**
     * @param touser         发送消息接收者    ，msgtype消息类型（文本/图片等），
     * @param application_id 应用编号。
     * @return String
     * @Title:创建微信发送请求post数据
     */
    public String createpostdata(String touser, String msgtype, int application_id, String contentKey, String contentValue) {
        IacsWeChatDataVo wcd = new IacsWeChatDataVo();
        wcd.setTouser(touser);
        wcd.setAgentid(application_id);
        wcd.setMsgtype(msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey, contentValue);
        if ("text".equals(msgtype)){
            wcd.setText(content);
        }else if("image".equals(msgtype)){
            wcd.setImage(content);
        }else if("voice".equals(msgtype)){
            wcd.setVoice(content);
        }else if("video".equals(msgtype)){
            content.put("title", "Title");
            content.put("description", "Description");
            wcd.setVideo(content);
        }else if("file".equals(msgtype)){
            wcd.setFile(content);
        }else if("news".equals(msgtype)){
            content.clear();
            Map<Object, Object> content2 = new HashMap<Object, Object>();
            content2.put("title", "中秋节礼品领取");
            content2.put("description", "今年中秋节公司有豪礼相送");
            content2.put("url", "www.baidu.com");
            content2.put("picurl", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3069259875,1190326871&fm=27&gp=0.jpg");
            ArrayList<Map> maps = new ArrayList<>();
            maps.add(content2);
            content.put("articles",maps);
            wcd.setNews(content);
        }else if("mpnews".equals(msgtype)){
            content.clear();
            Map<Object, Object> content2 = new HashMap<Object, Object>();
            content2.put("title", "中秋节礼品领取");
            content2.put("thumb_media_id", "2Xot7AS6ffNQaS-TSDiuxLQ8UcErYQSBSELFmnGaOzQtlPJgKIj_BjM0mHag4sM9M");
            content2.put("author", "yangzhanghui");
            content2.put("content_source_url", "www.baidu.com");
            content2.put("content", "今年中秋节公司有豪礼相送");
            content2.put("digest", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3069259875,1190326871&fm=27&gp=0.jpg");
            ArrayList<Map> maps = new ArrayList<>();
            maps.add(content2);
            content.put("articles",maps);
            wcd.setMpnews(content);
        }else if("textcard".equals(msgtype)){
            content.put("title", "领奖通知");
            content.put("description", "<div class=\"gray\">2016年9月26日</div> <div class=\"normal\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\"highlight\">请于2016年10月10日前联系行政同事领取</div>");
            content.put("url", "www.baidu.com");
            content.put("btntxt", "详情");
            wcd.setTextcard(content);
        }else if("markdown".equals(msgtype)){
            content.put("content", "\"您的会议室已经预定，稍后会同步到`邮箱` \n" +
                    "                >**事项详情** \n" +
                    "                >事　项：<font color=\\\"info\\\">开会</font> \n" +
                    "                >组织者：@miglioguan \n" +
                    "                >参与者：@miglioguan、@kunliu、@jamdeezhou、@kanexiong、@kisonwang \n" +
                    "                > \n" +
                    "                >会议室：<font color=\\\"info\\\">广州TIT 1楼 301</font> \n" +
                    "                >日　期：<font color=\\\"warning\\\">2018年5月18日</font> \n" +
                    "                >时　间：<font color=\\\"comment\\\">上午9:00-11:00</font> \n" +
                    "                > \n" +
                    "                >请准时参加会议。 \n" +
                    "                > \n" +
                    "                >如需修改会议信息，请点击：[修改会议信息](https://work.weixin.qq.com)\"");
            wcd.setMarkdown(content);
        }
        return gson.toJson(wcd);
    }

    /**
     * @param charset 消息编码    ，contentType 消息体内容类型，
     * @param url     微信消息发送请求地址，data为post数据，token鉴权token
     * @return String
     * @Title 创建微信发送请求post实体
     */
    public String post(String charset, String contentType, String url, String data, String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        httpPost = new HttpPost(url + token);
        httpPost.setHeader(CONTENT_TYPE, contentType);
        httpPost.setEntity(new StringEntity(data, charset));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info("call [{}], param:{}, resp:{}", url, data, resp);
        return resp;
    }
}