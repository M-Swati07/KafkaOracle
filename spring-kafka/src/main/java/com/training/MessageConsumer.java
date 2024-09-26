package com.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

	@Autowired
	MessageRepository messageRepository;
	
	@KafkaListener(topics = {"ofsstopic"}, groupId = "xyz")
	public void getAllMsg(String msg) {
		messageRepository.addMessage(msg);
		
		System.out.println("All Messages :"+messageRepository.getAllMessages());
	}
}
