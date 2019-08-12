package com.learn.yzh.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: yj->LogLevelConfig
 * @description: Log 配置
 * @author: yangzhanghui
 * @create: 2019-08-02 13:23
 **/
@Configuration
public class LogLevelConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
