package com.learn.yzh.test;

import com.learn.yzh.model.IacsUrlDataVo;
import com.learn.yzh.utils.SendWeChatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
public class WeChatTest{

    // corpid为每个企业都拥有唯一的corpid，获取此信息可在管理后台“我的企业”－“企业信息”下查看（需要有管理员权限）
    // corpsecret是企业应用里面用于保障数据安全的“钥匙”，每一个应用都有一个独立的访问密钥
    String corpid = "ww9ae8f547fd58949d";
    String corpsecret = "r-X1WtI8rZajqT5YnuZM0cPaAGRjgGsKjYRZy9iu_EQ";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Integer applicationId = 1000002;

    @Test
    public void testText() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "text", applicationId, "content", "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。" + "\n--------\n" + df.format(new Date()));
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
    @Test
    public void testImage() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "image", applicationId, "media_id", "2Xot7AS6ffNQaS-TSDiuxLQ8UcErYQSBSELFmnGaOzQtlPJgKIj_BjM0mHag4sM9M");
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
    @Test
    public void testVoice() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "voice", applicationId, "media_id", "2LknONmv3L6p0GCHnfiPJ7tBlnLHwFMsUN8O-0bn1G3Ezjm6iNBifm_WOozEGXjO_");
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
    @Test
    public void testVideo() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "video", applicationId, "media_id", "1014_a582f1c24e5e4a9d9f5215b84e513422");
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
    @Test
    public void testNews() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "news", applicationId, "media_id", "2Yxc_XJq-mK8H9D3LwjWt8q4q8-_1mhsAZMGN_MlgDz1rhO8XuDblF0X33VVPhfeq");
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
    @Test
    public void testMPNews() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "mpnews", applicationId, "media_id", "2Yxc_XJq-mK8H9D3LwjWt8q4q8-_1mhsAZMGN_MlgDz1rhO8XuDblF0X33VVPhfeq");
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
    @Test
    public void testTextCard() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "textcard", applicationId, "media_id", "2Yxc_XJq-mK8H9D3LwjWt8q4q8-_1mhsAZMGN_MlgDz1rhO8XuDblF0X33VVPhfeq");
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
    @Test
    public void testMarkDown() throws IOException {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        String token = msgUtils.getToken(corpid, corpsecret);
        String postdata = msgUtils.createpostdata("@all", "markdown", applicationId, "media_id", "2Yxc_XJq-mK8H9D3LwjWt8q4q8-_1mhsAZMGN_MlgDz1rhO8XuDblF0X33VVPhfeq");
        String resp = msgUtils.post("utf-8", SendWeChatUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postdata);
        System.out.println("发送微信的响应数据======>" + resp);
    }
}
