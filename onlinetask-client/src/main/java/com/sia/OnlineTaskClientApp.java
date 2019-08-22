package com.sia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//务必覆盖扫描包的范围
@SpringBootApplication(scanBasePackages = {"com.sia"})
public class OnlineTaskClientApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineTaskClientApp.class);

    public static void main(String[] args) {

        SpringApplication.run(OnlineTaskClientApp.class, args);
        LOGGER.info("OnlineTaskClient启动！");

    }

}