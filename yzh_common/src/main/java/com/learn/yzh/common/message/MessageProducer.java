package com.learn.yzh.common.message;

import com.learn.yzh.common.rocketMQ.RocketMQProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:MessageProducer
 *
 * @author yangli
 * @create 2017-12-18 17:20
 */
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

//    private String mqNameServer = "192.168.0.138:9876";

    /**
     * 发送MQ消息
     */
    public static void sendMq(String mqNameServer, String producerGroupName, Message message){
        try {
            SendResult sendResult = RocketMQProducer.getProducer(mqNameServer, producerGroupName).send(message);
            SendStatus status = sendResult.getSendStatus();
            logger.info("RocketMQProducer ==> messageTopic="+message.getTopic()+", messageId=" + sendResult.getMsgId() + ", status=" + status);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
