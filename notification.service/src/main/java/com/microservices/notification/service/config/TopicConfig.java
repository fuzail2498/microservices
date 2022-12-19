package com.microservices.notification.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
	@Bean
	public NewTopic testTopic() {
		return new NewTopic("notification-topic", 1, (short) 1);
	}

}
