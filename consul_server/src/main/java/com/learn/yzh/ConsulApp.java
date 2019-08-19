package com.learn.yzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients
@RestController
public class ConsulApp {
    public static void main( String[] args ){
        SpringApplication.run(ConsulApp.class, args);
    }

    @RequestMapping("/test")
    public String test(){
        return UUID.randomUUID().toString();
    }
}
