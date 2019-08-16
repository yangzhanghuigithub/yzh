package com.learn.yzh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: yj->WelcomeController
 * @description: welcome
 * @author: yangzhanghui
 * @create: 2019-08-11 16:54
 **/

@Controller
//@RequestMapping(value = "/")
public class WelcomeController {
    //退出的时候是get请求，主要是用于退出
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String login(){
        return "login/index";
    }
}
