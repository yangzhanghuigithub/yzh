package com.learn.yzh;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.learn.yzh.service.HelloService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by caibosi on 2018-08-02.
 */
@EnableDubbo
@SpringBootApplication
public class ConsumerMain {
    public static void main(String[] args) {
        ReferenceConfig<HelloService> referenceConfig = new ReferenceConfig<HelloService>();
        referenceConfig.setApplication(new ApplicationConfig("hello-service-consumer"));
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("192.168.0.123:2181,192.168.0.123:2182,192.168.0.123:2183,192.168.0.123:2184,192.168.0.123:2185,192.168.0.123:2186,192.168.0.123:2187");
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(HelloService.class);
        HelloService greetingService = referenceConfig.get();
        System.out.println(greetingService.hello());
    }
}
