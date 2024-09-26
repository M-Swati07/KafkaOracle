package com.training;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SimpleProducerUsingMetaData {

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
		(topicName,"hi");
		
		try {
			RecordMetadata metadata = producer.send(record).get();
			
			System.out.println("Message sent to partition no :"+metadata.partition()+"  and offset :"+metadata.offset());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		producer.close();
		
		System.out.println("Message sent successfully to kafka topic : "+topicName);
	}
}
