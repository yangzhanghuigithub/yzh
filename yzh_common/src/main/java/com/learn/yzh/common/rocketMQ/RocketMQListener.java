package com.learn.yzh.common.rocketMQ;

import com.learn.yzh.common.message.MessageHandlers;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * RocketMQ监听
 *
 * ClassName:RocketMQListener
 *
 * @author yangli
 * @create 2017-12-14 18:29
 */
public class RocketMQListener  implements MessageListenerConcurrently {
    private static final Logger log= LoggerFactory.getLogger(RocketMQListener.class);

    private MessageHandlers messageHandlers;

    public RocketMQListener() {

    }

    public RocketMQListener(MessageHandlers messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//        System.out.println("get data from rocketMQ:" + msgs);
        for (MessageExt message : msgs) {
            String msg = new String(message.getBody());
            messageHandlers.handle(msg);
            //System.out.println("msg data from rocketMQ:" + msg);
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}