package com.learn.yzh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @program: yzh->AuthController
 * @description: AuthContoller
 * @author: yangzhanghui
 * @create: 2019-08-15 18:44
 **/
@RestController
public class AuthController {
    @RequestMapping("/test")
    public String test(HttpServletRequest request){
        System.out.println("---------header---------");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            System.out.println(key + ": " + request.getHeader(key));
        }
        System.out.println("---------header---------");
        return "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh";
    }
}
