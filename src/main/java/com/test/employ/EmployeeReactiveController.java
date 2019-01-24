package com.test.employ;

import com.google.common.base.Strings;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * @program: client
 * @description:
 * @author: daniel
 * @create: 2019-01-14 21:44
 **/
@RestController
@RequestMapping("/employees")
public class EmployeeReactiveController {

	private final DefaultMQProducer producer = new DefaultMQProducer("client_demo");

	@PostConstruct
	public void init() {
		try {
			producer.setNamesrvAddr("alittlebear.cn:9876");
			producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/{id}")
	private Mono<String> getEmployeeById(@PathVariable String id) throws Exception{
		Message msg = new Message("client_getEmployeeById-request",
				id.getBytes(RemotingHelper.DEFAULT_CHARSET));

		SendResult sendResult = producer.send(msg);
		System.out.printf("request %s%n", sendResult);

		CacheUtil.put(id,"");

		return Mono.just(id).map(t -> {String value = null;
			while (Strings.isNullOrEmpty(value)) {
				value = CacheUtil.get(t);
			}
			return value;
		}).timeout(Duration.ofSeconds(3));

	}
}
