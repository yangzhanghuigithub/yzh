package com.learn.yzh;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.learn.yzh.service.HelloService;
import com.learn.yzh.service.HelloServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Created by caibosi on 2018-08-02.
 */
@EnableDubbo
@SpringBootApplication
public class ProviderMain {

    public static void main(String[] args) throws IOException {
        ServiceConfig<HelloService> serviceConfig = new ServiceConfig<HelloService>();
        serviceConfig.setApplication(new ApplicationConfig("hello-service-provider"));
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("192.168.0.125:2181");
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setInterface(HelloService.class);
        serviceConfig.setRef(new HelloServiceImpl());
        serviceConfig.export();
        System.in.read();
    }
}
