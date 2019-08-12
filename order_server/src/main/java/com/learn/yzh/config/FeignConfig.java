package com.learn.yzh.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Contract contract(){
        return new Contract.Default();
    }

}
