//package com.learn.yzh.config;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @program: yj->MqConsumer
// * @description: MqConsumer
// * @author: yangzhanghui
// * @create: 2019-08-05 21:32
// **/
//@Component
//@RabbitListener(queues = "hello")
//public class MqConsumer {
//
//    @RabbitHandler
//    public void process(String hello){
//        System.out.println("Reciever: " + hello);
//    }
//}
