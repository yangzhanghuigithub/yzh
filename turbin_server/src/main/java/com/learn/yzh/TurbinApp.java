package com.learn.yzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Turbin 服务!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableTurbine   // http://127.0.0.1:12086/hystrix  http://localhost:8005/turbine.stream
public class TurbinApp {
    public static void main( String[] args ) {
        SpringApplication.run(TurbinApp.class, args);
    }
}
