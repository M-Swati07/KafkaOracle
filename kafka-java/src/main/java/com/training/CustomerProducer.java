package com.training;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.training.model.CustomerKey;

public class CustomerProducer {

	public static void main(String[] args) {

		String topicName = "ofsstopic";

		
		// Set properties for kafka
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		kafkaProperties.put("key.serializer", "com.training.customserializer.CustomerKeySerializer");
		kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProperties.put("partitioner.class", "com.training.custom.CustomerCustomerPartitioner");

		// Produce a message
		Producer<CustomerKey, String> producer = new KafkaProducer<CustomerKey, String>(kafkaProperties);

		for (int i = 0; i < 10; i++) {
			RecordMetadata metadata;
			try {
				metadata = producer.send(
								new ProducerRecord<CustomerKey, String>
								(topicName, new CustomerKey("101", LocalDateTime.now()), "value"+i)).get();
				System.out.println(
						"Message sent to partition no :" + metadata.partition() + "  and offset :" + metadata.offset());
						
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

		
		producer.close();

		System.out.println("Message sent successfully to kafka topic : " + topicName);
	}
}
