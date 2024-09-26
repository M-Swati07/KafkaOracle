package com.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	@Autowired
	MessageConsumer messageConsumer;
	
	@Autowired
	MessageRepository messageRepository;
	
	@GetMapping("/getAll")
	public String getAll() {
		return messageRepository.getAllMessages();
	}
}
