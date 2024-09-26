package com.training;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class SimpleConsumer {

	public static void main(String[] args) {

		String topicName = "ofsstopic";

		// Set properties for kafka
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
//		kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());

		kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		//earliest, latest, none, anything else

		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProperties);
		
	//	consumer.seekToBeginning(consumer.assignment());
		consumer.subscribe(Arrays.asList("ofsstopic"));
		
		ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofSeconds(10));
		
		for(ConsumerRecord<String, String> temp:consumerRecords) {
			System.out.println(temp);
		}
		
		System.out.println("Successfully fetched all the message from topic :"+topicName);
	}
}
