package com.learn.yzh.common.rocketMQ;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * RocketMQ生产端
 *
 * ClassName:RocketMQProducer
 * @version 2018-12-01 添加可支持多个Producer的支持。
 * @author licho
 * @author yangli
 * @create 2017-12-14 18:31
 */
public class RocketMQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);

    private static DefaultMQProducer sender;

    private static final Map<String,DefaultMQProducer> senderMap=new ConcurrentHashMap<>();

    private static final Integer lock = 1;

    public static DefaultMQProducer getProducer(String nameServer, String producerGroupName) {
        if(!senderMap.containsKey(producerGroupName)) {
            synchronized (lock) {
                if(!senderMap.containsKey(producerGroupName)){
                    DefaultMQProducer current = new DefaultMQProducer(producerGroupName);
                    current.setNamesrvAddr(nameServer);
                    current.setInstanceName(UUID.randomUUID().toString());
                    current.setVipChannelEnabled(false);
                    logger.info("正在启动生产者：{}",producerGroupName);
                    try {
                        current.start();
                        senderMap.put(producerGroupName,current);
                        logger.info("启动生产者:{}成功",producerGroupName);
                        return current;
                    } catch (MQClientException e) {
                        logger.error("启动mq生产者:{}失败,异常信息:{}",producerGroupName, e);
                        throw new RuntimeException("启动mq失败", e);
                    }
                }
            }
        }
        return senderMap.get(producerGroupName);
    }
}
