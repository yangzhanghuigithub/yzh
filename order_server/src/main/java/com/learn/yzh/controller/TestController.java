package com.learn.yzh.controller;

import com.learn.yzh.config.AggregateCompnent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: yj->TestController
 * @description: Test
 * @author: yangzhanghui
 * @create: 2019-08-02 16:12
 **/
@Controller
@RequestMapping("/index")
@RefreshScope
public class TestController {

    @Value("${version}")
    private String username;

    @Autowired
    private AggregateCompnent aggregateCompnent;


    @RequestMapping(value = "/test8",method = RequestMethod.GET)
    @ResponseBody
    public String test8(){
        System.out.println(username);
        return username;
    }

    @RequestMapping("test2")
    public String test2() throws InterruptedException, IllegalAccessException, InvocationTargetException {
        return aggregateCompnent.getUserFinal("3").toString();
    }

}
