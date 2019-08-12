package com.learn.yzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Config 服务
 *
 */

@EnableFeignClients(basePackages = {"com.learn.yzh"})
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
@Configuration
@ServletComponentScan
@EnableCircuitBreaker // 启动断路器，如果要监控hystrix的流必须开启此注解
@EnableHystrixDashboard // 开启dashboard，通过图形化的方式监控: 查看 http://127.0.0.1:12082/hystrix.stream
public class ConfigApp {
    public static void main( String[] args ) {
        SpringApplication.run(ConfigApp.class, args);
    }

    @RequestMapping("/test")
    public String test(){
        return "hello,This is config service";
    }
}