package com.microservices.notification.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class OrderNotificationEventListener {

	@KafkaListener(topics = "notification-topic", groupId = "GID_1", containerFactory = "orderNotificationKafkaListenerContainerFactory")
	public void consume(OrderNotificationEvent event) {
		log.info("Inside NOTIFICATON_SERVICE :: I have listened the order : "+event.getOrderNumber());
		System.out.println(event);
	}


}
