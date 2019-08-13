package com.learn.yzh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: yzh->MainController
 * @description: 程序主入口
 * @author: yangzhanghui
 * @create: 2019-08-13 15:29
 **/
@Controller
public class MainController {

//    @RequestMapping("/")
//    public String root() {
//        return "redirect:/index/login";
//    }
//
//    @RequestMapping("/index")
//    public String index() {
//        return "index/login";
//    }

    @RequestMapping("/login")
    public String login() {
        return "/login/index";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute( "loginError"  , true);
        return "login";
    }

    @GetMapping("/401")
    public String accessDenied() {
        return "401";
    }

    @GetMapping("/user/common")
    public String common() {
        return "user/common";
    }

    @GetMapping("/user/admin")
    public String admin() {
        return "user/admin";
    }


}