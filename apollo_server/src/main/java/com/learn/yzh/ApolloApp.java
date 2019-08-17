package com.learn.yzh;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Configuration
@EnableApolloConfig(value = "yzh", order = 10)
public class ApolloApp {
    public static void main( String[] args ){
        SpringApplication.run(ApolloApp.class);
    }
}
