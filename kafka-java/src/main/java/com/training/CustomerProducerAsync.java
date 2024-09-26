package com.training;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.training.customserializer.CustomerKeyDeserializer;
import com.training.customserializer.CustomerKeySerializer;
import com.training.model.CustomerKey;

public class CustomerProducerAsync {

	public static void main(String[] args) {

		String topicName = "ofsstopic";

		// Serde
		Serde<CustomerKey> customerSerde = Serdes.serdeFrom(new CustomerKeySerializer(), new CustomerKeyDeserializer());

		// Set properties for kafka
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		// kafkaProperties.put("key.serializer",
		// "com.training.customserializer.CustomerKeySerializer");
		kafkaProperties.put("key.serializer", customerSerde.serializer().getClass());

		kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProperties.put("partitioner.class", "com.training.custom.CustomerCustomerPartitioner");

		// Produce a message
		Producer<CustomerKey, String> producer = new KafkaProducer<CustomerKey, String>(kafkaProperties);

		for (int i = 0; i < 10; i++) {
			// A Future represents the result of an asynchronous computation
			Future<RecordMetadata> metadata;
			metadata = producer.send(new ProducerRecord<CustomerKey, String>(topicName,
					new CustomerKey("101", LocalDateTime.now()), "value" + i), new MyProducerCallback());
			try {
				System.out.println("Message sent to partition no :" + metadata.get().partition());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		producer.close();
		System.out.println("Message sent successfully to kafka topic : " + topicName);
	}
}

/*
 * A callback interface that the user can implement to allow code to execute
 * when the request is complete. This callback will generally execute in the
 * background I/O thread so it should be fast.
 */
class MyProducerCallback implements Callback {
	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if (exception != null) {
			System.out.println("Async operation failed");
		} else {
			System.out.println("Async operation completed successfully!!");
			Thread t1 = new Thread() {
				public void run() {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Hello");
				}
			};
			t1.start();
			System.out.println("Async operation started a thread!!");
		}
	}
}
