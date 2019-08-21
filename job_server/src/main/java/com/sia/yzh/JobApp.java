package com.sia.yzh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication(scanBasePackages = "com.sia")
//@EnableEurekaClient
@RestController
@EnableScheduling
//@MapperScan("com.sia")
@ComponentScan(basePackages = {"com.sia"})
//@EnableFeignClients //启用feign进行远程调用
public class JobApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobApp.class);

    public static void main( String[] args ) {
        SpringApplication.run(JobApp.class, args);
    }

    @Bean
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(value = "/test")
    public String test(){
        System.out.print("this is server_job");
        return "this is server_job";
    }
}
