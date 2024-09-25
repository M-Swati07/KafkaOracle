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


create a topic named generalmessage
send three message to generalmessage
display all messages from topic generalmessage

--Verify all the topics using list command



10 minutes



















