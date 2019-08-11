package com.learn.yzh.common.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 系统常量类
 * ClassName:SystemConstants
 *
 * @author bilaoye
 * @create 2017-11-20 19:12
 */
@Component
@PropertySource("classpath:/system.properties")
@ConfigurationProperties(prefix = "key")
public class SystemConstants {

    private String imageBaseUrl;
    private String ios;//app 系统IOS
    private String android;//app 系统Android
    private String des3SecretKey;//DES3生成密钥
    private String des3Iv;//DES3向量
    private String token;//登录验证
    private String UserToken;//

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getDes3SecretKey() {
        return des3SecretKey;
    }

    public void setDes3SecretKey(String des3SecretKey) {
        this.des3SecretKey = des3SecretKey;
    }

    public String getDes3Iv() {
        return des3Iv;
    }

    public void setDes3Iv(String des3Iv) {
        this.des3Iv = des3Iv;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }
}