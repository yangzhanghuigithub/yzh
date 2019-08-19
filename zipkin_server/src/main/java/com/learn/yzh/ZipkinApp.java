package com.learn.yzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import zipkin.server.internal.EnableZipkinServer;
import zipkin.storage.mysql.MySQLStorage;

import javax.sql.DataSource;

/**
 * Zipkin 服务!
 *
 */
@SpringBootApplication
@EnableZipkinServer //启动ZipkinServer段
@EnableEurekaClient
public class ZipkinApp {
    public static void main( String[] args ){
        SpringApplication.run(ZipkinApp.class, args);
    }

    @Bean
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
    }
}
