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


