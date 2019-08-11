package com.learn.yzh.common.rocketMQ;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * RocketMQ消费端
 * <p>
 * <p>
 * ClassName:RocketMQConsumer
 *
 * @author yangli
 * @create 2017-12-14 18:28
 */
public class RocketMQConsumer {

	private static final Logger logger = LoggerFactory.getLogger(RocketMQConsumer.class);

	public synchronized static void subscribe(MessageListener listener, String nameServer,String consumerGroupName, String topics, String tags) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroupName);
		consumer.setNamesrvAddr(nameServer);
		try {
			consumer.setInstanceName(UUID.randomUUID().toString());
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			consumer.setMessageModel(MessageModel.CLUSTERING);
			consumer.registerMessageListener((MessageListenerConcurrently) listener);
			consumer.setVipChannelEnabled(false);

			consumer.subscribe(topics, tags);

			consumer.start();
			logger.info("RocketMQConsumer Started! group=" + consumer.getConsumerGroup() + " instance=" + consumer.getInstanceName());
		} catch (MQClientException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public synchronized static void subscribe(MessageListener listener, String nameServer,String consumerGroupName, String topics, String tags, long consumeTimeOut) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroupName);
		consumer.setNamesrvAddr(nameServer);
		try {
			consumer.setConsumeTimeout(consumeTimeOut); // 默认超时时间
			consumer.setInstanceName(UUID.randomUUID().toString());
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			consumer.registerMessageListener((MessageListenerConcurrently) listener);

			consumer.subscribe(topics, tags);

			consumer.start();
			logger.info("RocketMQConsumer Started! group=" + consumer.getConsumerGroup() + " instance=" + consumer.getInstanceName());
		} catch (MQClientException e) {
			logger.error(e.getMessage(), e);
		}
	}

//	public void init() {
//		consumer = new DefaultMQPushConsumer();
//		consumer.setNamesrvAddr(nameServer);
//		try {
//			consumer.subscribe(topics, "*");
//		} catch (MQClientException e) {
//			e.printStackTrace();
//		}
//		consumer.setInstanceName(UUID.randomUUID().toString());
//		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//		consumer.registerMessageListener((MessageListenerConcurrently) this.listener);
//
//		try {
//			consumer.start();
//
//		} catch (MQClientException e) {
//			e.printStackTrace();
//		}
//		logger.info("RocketMQConsumer Started! group=" + consumer.getConsumerGroup() + " instance=" + consumer.getInstanceName());
//	}

//	public void shutdown() {
//		if (this.consumer != null) {
//			this.consumer.shutdown();
//		}
//	}


}