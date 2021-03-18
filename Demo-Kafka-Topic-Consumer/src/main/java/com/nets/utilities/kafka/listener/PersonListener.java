package com.nets.utilities.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PersonListener {
	public PersonListener() {
		System.out.println("I have initialized.............................");
	}
	@KafkaListener(
			  topics = "personperson")
			public void listen(String message) {
			    System.out.println("Recived Message in filtered listener: " + message);
			}
}
