package com.learn.yzh.model;

/**
 * @ClassName: IacsUrlDataVo
 * @Author: shenlele
 * @Date: 2018/10/11 11:01
 * @Description:
 */
public class IacsUrlDataVo {

    String corpid;

    String corpsecret;

    String Get_Token_Url;

    String SendMessage_Url;

    String upload_temp_url;

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getCorpsecret() {
        return corpsecret;
    }

    public void setCorpsecret(String corpsecret) {
        this.corpsecret = corpsecret;
    }

    public void setGet_Token_Url(String corpid, String corpsecret) {
        this.Get_Token_Url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret;
    }

    public String getGet_Token_Url() {
        return Get_Token_Url;
    }

    public String getSendMessage_Url() {
        SendMessage_Url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?type=file&access_token=";
        return SendMessage_Url;
    }

    public String getUpload_temp_url() {
        String UploadTemp_Url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=";
        return UploadTemp_Url;
    }
}