package com.learn.yzh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: yzh->ConfigClientController
 * @description: ConfigClientController
 * @author: yangzhanghui
 * @create: 2019-08-17 18:03
 **/
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigClientController {

    @Value("${yzh}")
    private String configinfo;

    @RequestMapping("getConfiginfo")
    public String getConfiginfo(){
        return configinfo;
    }
}
