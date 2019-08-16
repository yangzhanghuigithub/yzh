package com.learn.yzh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: yj->WebSocketController
 * @description: 模拟多人聊天
 * @author: yangzhanghui
 * @create: 2019-07-29 20:53
 **/
@Controller
@RequestMapping("/socket")
public class WebSocketController {

    /**
     * 第一个用户
     *
     * @param request
     * @return
     */
    @RequestMapping("/chat1")
    public String chat1(HttpServletRequest request) {
        // 假设用户tom登录,存储到session中
        request.getSession().setAttribute("WEBSOCKET_USERNAME", "tom");
        return "index/chat1";
    }

    /**
     * 第二个用户登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/chat2")
    public String chat2(HttpServletRequest request) {
        // 假设用户jerry登录,存储到session中
        request.getSession().setAttribute("WEBSOCKET_USERNAME", "jerry");
        return "index/chat2";
    }

    /**
     * 第三个用户登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/chat3")
    public String chat3(HttpServletRequest request) {
        // 假设用户jack登录,存储到session中
        request.getSession().setAttribute("WEBSOCKET_USERNAME", "jack");
        return "index/chat3";
    }
}