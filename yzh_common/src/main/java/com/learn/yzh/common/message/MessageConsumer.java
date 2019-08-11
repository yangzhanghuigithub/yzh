package com.learn.yzh.common.message;

import com.learn.yzh.common.rocketMQ.RocketMQConsumer;
import com.learn.yzh.common.rocketMQ.RocketMQListener;

/**
 * ClassName:MessageConsumer
 *
 * @author yangli
 * @create 2017-12-18 17:05
 */
public class MessageConsumer {

    //private  static String mqNameServer = "192.168.0.138:9876";

    //String mqTopics = "MQ-MSG-TOPICS-TEST";

   // String consumerMqGroupName = "CONSUMER-MQ-GROUP";
    /**
     * 启动MQ消息队列
     */
    public void startMqConsumer(String mqNameServer,String consumerGroupName, String mqTopics, String tags,MessageHandlers messageHandlers){

        RocketMQListener mqListener = new RocketMQListener(messageHandlers);
        RocketMQConsumer.subscribe(mqListener, mqNameServer, consumerGroupName, mqTopics, tags);

    }
    public void startMqConsumer(String mqNameServer,String consumerGroupName, String mqTopics, String tags,MessageHandlers messageHandlers, long consumeTimeOut){

        RocketMQListener mqListener = new RocketMQListener(messageHandlers);
        RocketMQConsumer.subscribe(mqListener, mqNameServer, consumerGroupName, mqTopics, tags, consumeTimeOut);

    }
}
