package com.training;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MessageRepository {

	private List<String> messages = new ArrayList<String>();

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public void addMessage(String message) {
		messages.add(message);
	}

	public String getAllMessages() {
		return messages.toString();
	}

}
