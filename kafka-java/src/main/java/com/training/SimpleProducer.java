package com.training;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SimpleProducer {

	public static void main(String[] args) {
		
		String topicName = "ofsstopic";
		
		//Set properties for kafka
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		//Produce a message
		
		Producer<String, String> producer = new KafkaProducer<String, String>(kafkaProperties);
		
		ProducerRecord<String, String> record = new ProducerRecord<String, String>
		(topicName, "key1","SayHelloFromOFSS");
		
		producer.send(record);
		
		producer.close();
		
		System.out.println("Message sent successfully to kafka topic : "+topicName);
	}
}
