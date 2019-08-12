//package com.learn.yzh.config;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * @program: yj->RabbitComponent
// * @description: RabbitTest
// * @author: yangzhanghui
// * @create: 2019-08-05 21:27
// **/
//@Component
//public class MqSender {
//
//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    public void send() {
//        String context = "hello " + new Date();
//        System.out.println("Sender : " + context);
//        rabbitTemplate.convertAndSend("hello", context);
//    }
//
//
//}
