package com.learn.yzh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: yzh->SecuritySecureConfig
 * @description: SecuritySecureConfig
 * @author: yangzhanghui
 * @create: 2019-08-12 21:22
 **/
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests().antMatchers("/actuator/**","/encrypt","/decrypt","/encrypt/**","/decrypt/**").permitAll().and().logout().permitAll();//配置不需要登录验证路径
    }
}