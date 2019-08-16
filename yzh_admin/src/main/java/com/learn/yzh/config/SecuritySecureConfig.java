package com.learn.yzh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: yzh->SecuritySecureConfig
 * @description: SecuritySecureConfig
 * @author: yangzhanghui
 * @create: 2019-08-15 21:05
 **/
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests().antMatchers("/**").permitAll().and().logout().permitAll();//配置不需要登录验证路径
        http.headers().frameOptions().disable();
        http.csrf().disable();
    }
}
