package com.learn.yzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Job Server
 *
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableScheduling
@MapperScan("com.learn.yzh")
@ComponentScan(basePackages = {"com.learn.yzh"})
@EnableFeignClients //启用feign进行远程调用
public class JobApp {
    public static void main( String[] args ) {
        SpringApplication.run(JobApp.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(value = "/test")
    public String test(){
        System.out.print("this is server_job");
        return "this is server_job";
    }
}
