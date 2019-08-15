package com.learn.yzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Zuul 服务
 *
 */
@SpringBootApplication(scanBasePackages = "com.learn.yzh",
        exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = {"com.learn.yzh"})
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@EnableCircuitBreaker // 启动断路器，如果要监控hystrix的流必须开启此注解
@EnableHystrixDashboard // 开启dashboard，通过图形化的方式监控: 查看 http://127.0.0.1:12082/hystrix.stream
public class ZuulApp extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApp.class, args);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/login","/client/**").
                permitAll().anyRequest().authenticated().and().csrf().disable();
    }

    @Bean
    public PatternServiceRouteMapper serviceRouteMapper(){
        return  new PatternServiceRouteMapper("","");
    }

    @Bean
    @LoadBalanced // 实现负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/test")
    public String test(){
        return "hello,This is zuul service";
    }
}

