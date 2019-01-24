package com.test.employ;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @program: client
 * @description:
 * @author: daniel
 * @create: 2019-01-24 10:25
 **/
@Service
public class MQUtil {
	final static DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_grp123");

	@PostConstruct
	public void consume() throws Exception{
		consumer.setNamesrvAddr("alittlebear.cn:9876");
		consumer.subscribe("client_getEmployeeById-request", "*");
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				String key = new String(msgs.get(0).getBody());
				System.out.println("put cache:" + key);
				CacheUtil.put(key, "Rocketmq");
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		consumer.start();
		System.out.println("consumer start");
	}
}
