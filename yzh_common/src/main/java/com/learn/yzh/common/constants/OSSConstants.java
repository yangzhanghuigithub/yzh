package com.learn.yzh.common.constants;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * ClassName:OSSConstants
 *
 * @author bilaoye
 * @create 2018-03-13 11:04
 */
/*@ComponentScan
@Configuration
@ConfigurationProperties( prefix = "oss")*/
//@PropertySource(value = "classpath:/application-dev.properties")
public class OSSConstants {
    @Value("${oss.bucket}")
    public   String bucket;
    @Value("${oss.endpoint}")
    public  String endpoint;
    @Value("${oss.key}")
    public  String key;
    @Value("${oss.secret}")
    public  String secret;

    @Value("${oss.bucket.app}")
    public   String bucketapp;
    @Value("${oss.endpoint.app}")
    public  String endpointapp;
    @Value("${oss.url}")
    public  String ossUrl;
}