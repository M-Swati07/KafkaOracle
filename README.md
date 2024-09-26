"# ofss202406" 

What is Kafka ?

Data Storage


Fault tolerance in Apache Kafka is the ability of the system to continue to function and serve data reliably even when some components fail.


Broker - Kafka Server
Producer - produces the messages
Consumer - consumes the messages
zookeeper - ZooKeeper is a key-value store that works with Kafka to form a complete Kafka cluster. It acts as a centralized service that provides coordination, configuration, and other services for Kafka


---------------------------------

Install 


JDK 17 
Eclipse IDE 2024-06
Kafka 		https://downloads.apache.org/kafka/3.8.0/kafka_2.13-3.8.0.tgz
Maven		https://dlcdn.apache.org/maven/maven-3/3.9.8/binaries/apache-maven-3.9.8-bin.zip	- just unzip
Rancher		https://rancherdesktop.io/		-  download and install
** install rancher it in personal laptops only, not official

Use case : I have to send message "Hi OFSS" to kafka


PATH = E:\kafka\bin\windows

Step 1: start zookeeper

C:\Users\tufai>zookeeper-server-start e:\kafka\config\zookeeper.properties



Error : 
zookeeper-server-start C:\Users\Ashish Rai\Desktop\Kafka\kafka_2.13-3.8.0\kafka_2.13-3.8.0\config\zookeeper.properties
The input line is too long.
The syntax of the command is incorrect.

Solution :


zookeeper-server-start D:\kafka\config\zookeeper.properties

zookeeper is running on 2181 port


-------------------------------------------------------------------------------------

Step 2: start kafka server 


D:\kafka\config\server.properties

broker.id = 0


advertised.listeners=PLAINTEXT://localhost:9092
port = 9092
advertised.host.name = localhost 

zookeeper.connect=localhost:2181


listeners is what the broker will use to create server sockets

advertised.listeners is what clients will use to connect to the brokers

kafka-server-start e:\kafka\config\server.properties

Step 3 : Topic

C:\Users\tufai>kafka-topics --bootstrap-server localhost:9092 --create --topic ofsstopic
Created topic ofsstopic.

C:\Users\tufai>kafka-topics --bootstrap-server localhost:9092 --list
ofsstopic

Step 4: Send Message to a topic

kafka-console-producer --broker-list localhost:9092 --topic ofsstopic

kafka-console-producer --bootstrap-server localhost:9092 --topic ofsstopic

Step 5 : Recieve the message from a topic

kafka-console-consumer --bootstrap-server localhost:9092 --topic ofsstopic

kafka-console-consumer --bootstrap-server localhost:9092 --topic ofsstopic --from-beginning


Hands on
============


--create a topic named generalmessage
--send three message to generalmessage
--display all messages from topic generalmessage

--Verify all the topics using list command



---------------------------
Use case : How fault tolerence and scalibility works

two more brokers -
0  9092	server.properties

===============================================
border.id =1

 1 9093    server2.properties

listeners=PLAINTEXT://localhost:9093
port = 9093
advertised.host.name = localhost 

zookeeper.connect=localhost:2181
log.dirs=/tmp/kafka-logs2
===============================================

 2 9094    server3.properties

listeners=PLAINTEXT://localhost:9094
port = 9094
advertised.host.name = localhost 

zookeeper.connect=localhost:2181
log.dirs=/tmp/kafka-logs3


kafka-server-start e:\kafka\config\server2.properties

kafka-server-start e:\kafka\config\server3.properties


=====================================================
To check available brokers

C:\Users\tufai>zookeeper-shell localhost:2181 ls /brokers/ids
Connecting to localhost:2181

WATCHER::

WatchedEvent state:SyncConnected type:None path:null
[0, 1, 2]


========================================================


Hands on

create two topics named greeting and broadcasts topics in broker running on port 9093


create two topics named products and employees topics in broker running on  port 9094


send one messages in each topics and create consumers to views.




15 minutes


----------------------------------------------------------------------------------------------------------------



C:\Users\tufai>kafka-topics --bootstrap-server localhost:9093 --create --topic datatopic --partitions 2 --replication-factor=5
Error while executing topic command : Replication factor: 5 larger than available brokers: 3.
[2024-09-25 15:10:20,345] ERROR org.apache.kafka.common.errors.InvalidReplicationFactorException: Replication factor: 5 larger than available brokers: 3.
 (org.apache.kafka.tools.TopicCommand)



C:\Users\tufai>kafka-topics --bootstrap-server localhost:9093 --create --topic datatopic --partitions 5 --replication-factor=3
Created topic datatopic.




Partition : enables messages to be split in parallel acrosss several brokers in the cluster

kafka will be able to scale and support multiple consumers and producers at the same time.


kafka-topics --bootstrap-server localhost:9093 --create --topic datatopic --partitions 3


kafka-topics --bootstrap-server localhost:9093 --create --topic orcltopic --partitions 3



Partitioning takes the single topic log and breaks it into multiple logs, each of which can live on a separate node in the Kafka cluster. This way, the work of storing messages, writing new messages, and processing existing messages can be split among many nodes in the cluster.


Replication-factor


kafka-topics --bootstrap-server localhost:9093 --create --topic datatopic --partitions 5 --replication-factor=3

kafka-topics --bootstrap-server localhost:9092 -describe --topic datatopic


======================================================================================

Partitions = Desired Throughput / partition speed

default partition speed is 10 MB/s 

desired message throughput  5 TB per day i.e rougly 58 MB/s


Partition :  6 partition


===========================================================================================


Consumer Group


What is a consumer group in Apache Kafka? 
Consumer groups allow Kafka consumers to work together and process events from a topic in parallel.
 Consumers are assigned a subset of partitions from a topic or set of topics and can parallelize the processing of those events.

config/consumer.properties
group.id=ofss-consumer-group

Save and close

kafka-console-consumer --bootstrap-server localhost:9092 --topic ofsstopic



---list the consumer  groups

kafka-consumer-groups --list --bootstrap-server localhost:9092

Test
------
C:\Users\tufai>kafka-console-producer --bootstrap-server localhost:9092 --topic ofsstopic


kafka-console-consumer --bootstrap-server localhost:9092 --topic ofsstopic  
kafka-console-consumer --bootstrap-server localhost:9092 --topic ofsstopic --from-beginning

cmd
kafka-console-consumer --bootstrap-server localhost:9092 --topic ofsstopic --from-beginning --group ofss-consumer-group


cmd
kafka-console-consumer --bootstrap-server localhost:9092 --topic ofsstopic --from-beginning --group ofss-consumer-group
** message once consumed by one consumer in a group will not be available to other consumer




=================================
Old Kafka way

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TufailTestTopic



=====================================================================


Zookeeper needed to create new topics , but messaging can be done without zookeeper

***Shutdown the zookeeper

C:\Users\tufai>kafka-topics --bootstrap-server localhost:9092 --create --topic ofsstopic
Error while executing topic command : Topic 'ofsstopic' already exists.
[2024-09-25 17:34:43,043] ERROR org.apache.kafka.common.errors.TopicExistsException: Topic 'ofsstopic' already exists.
 (org.apache.kafka.tools.TopicCommand)

C:\Users\tufai>kafka-topics --bootstrap-server localhost:9092 --create --topic abctopic
Error while executing topic command : Call(callName=createTopics, deadlineMs=1727265954605, tries=1, nextAllowedTryMs=1727265954719) timed out at 1727265954617 after 1 attempt(s)
[2024-09-25 17:36:01,724] ERROR org.apache.kafka.common.errors.TimeoutException: Call(callName=createTopics, deadlineMs=1727265954605, tries=1, nextAllowedTryMs=1727265954719) timed out at 1727265954617 after 1 attempt(s)
Caused by: org.apache.kafka.common.errors.DisconnectException: Cancelled createTopics request with correlation id 3 due to node 0 being disconnected
 (org.apache.kafka.tools.TopicCommand)




Kafka using java

<!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>3.8.0</version>
</dependency>

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


-----------------


Consume

Error :
Exception in thread "main" org.apache.kafka.common.errors.InvalidGroupIdException: To use the group management or offset commit APIs, you must provide a valid group.id in the consumer configuration.

Solution :
kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());




--------------------------------------------------------------------------

Custom Partition

How to attach a key to specific partition ?


Step 1: Create a custom partitioner :
package com.training.custom;

import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner extends DefaultPartitioner{

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
	
		//return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
		return 3;

	}
}


Step 2: 

		kafkaProperties.put("partitioner.class", "com.training.custom.CustomPartitioner");




Real time use cases ?

Amazon

	customer

		Customer
		10 transaction


	NAME	- 0 partition

	transaction1 	-5 partition
	transaction7	- 8 partition



Requirement : Put one customer details including name and all his/her transaction in one partition.




What is serde in kafka 

Serde is a framework for serializing and deserializing Rust data structures efficiently and generically.

The Apache Kafka provides a Serde interface, which is a wrapper for serializer and deserializer of a data type.


custom partition
custom serializer
custom deserializer




==============================================================

Hands on : Read data from specific partition in a topic


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
		int targetPartition = 3;
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



=====================Serde Demo


package com.training.customserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.model.CustomerKey;

public class CustomerKeyDeserializer implements Deserializer<CustomerKey> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public CustomerKey deserialize(String topic, byte[] bytes) {
		CustomerKey data = null;
		if (Objects.isNull(bytes)) {
			return null;
		}

		try {
			data = objectMapper.treeToValue(objectMapper.readTree(bytes), CustomerKey.class);
		} catch (JsonProcessingException e) {
			throw new SerializationException("Some problem occurred:" + e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

}


----------------------------------------

package com.training;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.training.customserializer.CustomerKeyDeserializer;
import com.training.customserializer.CustomerKeySerializer;
import com.training.model.CustomerKey;

public class CustomerProducer {

	public static void main(String[] args) {

		String topicName = "ofsstopic";

		//Serde
		Serde<CustomerKey> customerSerde = Serdes.serdeFrom(new CustomerKeySerializer(), new CustomerKeyDeserializer());
		
		// Set properties for kafka
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
	//	kafkaProperties.put("key.serializer", "com.training.customserializer.CustomerKeySerializer");
		kafkaProperties.put("key.serializer", customerSerde.serializer().getClass());

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




Asynchronous message in kafka
==============================

package com.training;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.training.customserializer.CustomerKeyDeserializer;
import com.training.customserializer.CustomerKeySerializer;
import com.training.model.CustomerKey;

public class CustomerProducer {

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
			RecordMetadata metadata;

			try {
				metadata = producer.send(new ProducerRecord<CustomerKey, String>(topicName,
						new CustomerKey("101", LocalDateTime.now()), "value" + i)).get();
				System.out.println(
						"Message sent to partition no :" + metadata.partition() + "  and offset :" + metadata.offset());
				new A().display();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		producer.close();

		System.out.println("Message sent successfully to kafka topic : " + topicName);
	}
}

class A {
	public void display() {
		System.out.println("Display called");
		Thread t1 = new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Hello");
				
			}
		};
		t1.start();
	}
}


-------------------------------------------
  
  
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
						// TODO Auto-generated catch block
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

  








  Use case : To post message from helidion or any spring application.


application.yml

server:
 port: 9090

app:
 topic:
  name: ofsstopic

spring:
 kafka:
   producer:
    bootstrap-servers: localhost:9092
    key-serializer: org.apache.kafka.common.serialization.StringSerializer
    value-serializer: org.apache.kafka.common.serialization.StringSerializer

   consumer:
    bootstrap-servers: localhost:9092
    key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    value-deserializer: org.apache.kafka.common.serialization.StringDeserializer


-----------------


































