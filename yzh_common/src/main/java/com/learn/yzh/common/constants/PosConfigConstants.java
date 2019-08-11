package com.learn.yzh.common.constants;

import org.springframework.beans.factory.annotation.Value;
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
@PropertySource("classpath:/phenix.properties")
@ConfigurationProperties(prefix = "key")
public class PosConfigConstants {

   public static String channelCode;
   public static String version;
   public static String secretkey;
   public static String prefixUrl;

    public static String getChannelCode() {
        return channelCode;
    }

    public static void setChannelCode(String channelCode) {
        PosConfigConstants.channelCode = channelCode;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        PosConfigConstants.version = version;
    }

    public static String getSecretkey() {
        return secretkey;
    }

    public static void setSecretkey(String secretkey) {
        PosConfigConstants.secretkey = secretkey;
    }

    public static String getPrefixUrl() {
        return prefixUrl;
    }

    public static void setPrefixUrl(String prefixUrl) {
        PosConfigConstants.prefixUrl = prefixUrl;
    }
}