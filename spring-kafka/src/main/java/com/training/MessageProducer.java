package com.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate ;
	
	public void saveMessage(String message) {
			kafkaTemplate.send("ofsstopic", message);
	}
}
