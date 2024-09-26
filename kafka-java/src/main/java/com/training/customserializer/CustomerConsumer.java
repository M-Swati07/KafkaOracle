package com.training.customserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class CustomerConsumer {

	public static void main(String[] args) {

		String topicName = "ofsstopic";
		int targetPartition = 2;
		// Set properties for kafka
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "ofss-consumer-group");
		kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProperties);
		//consumer.seekToBeginning(consumer.assignment());
		TopicPartition partition = new TopicPartition(topicName, targetPartition);
		consumer.assign(Arrays.asList(partition));
		ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(2));
		for (ConsumerRecord<String, String> temp : consumerRecords) {
			System.out.println(temp);
		}
		consumer.close();
		System.out.println("Successfully fetched all the message from topic :" + topicName);
	}
}
