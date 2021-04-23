# KAFKA

## Setup
### Download below files
1. Download and install or some software to extract tar.gz files - http://www.7-zip.org/download.html
2. Download ZooKeeper version (3.7.0) http://zookeeper.apache.org/releases.html
3. Download Kafka version (2.12-2.7.0) http://kafka.apache.org/downloads.html

#### ZooKeeper
1. Extract downloaded zookeeper to C:/, like C:\apache-zookeeper-3.7.0
2. Go to ZooKeeper config - C:\apache-zookeeper-3.7.0\conf and Rename file “zoo_sample.cfg” to “zoo.cfg”
3. Open file - C:\apache-zookeeper-3.7.0\conf\zoo.cfg and Edit dataDir=/tmp/zookeeper to C:\apache-zookeeper-3.7.0\data
4. Go to Start, search for "Edit environment variables for your account"
    * Add User Variable ZOOKEEPER_HOME = C:\apache-zookeeper-3.7.0 
    * Edit the User Variable named “Path” and add %ZOOKEEPER_HOME%\bin
5. Open cmd and enter zkserver to run ZooKeeper server, it will start the ZooKeeper server on port 2181 (port can be changed in C:\apache-zookeeper-3.7.0\conf\zoo.cfg file)

#### Kafka
1. Extract downloaded kafka to C:/, like C:\kafka_2.12-2.7.0
2. Go to Kafka config - C:\kafka_2.12-2.7.0\config and Rename file “zoo_sample.cfg” to “zoo.cfg”
3. Open file - C:\kafka_2.12-2.7.0\config\server.properties and Edit log.dirs=/tmp/kafka-logs” to log.dirs=log.dirs=C:/kafka_2.12-2.7.0/kafka-logs
4. Go to Start, search for "Edit environment variables for your account"
    * Add User Variable KAFKA_HOME = C:\kafka_2.12-2.7.0
    * Edit the User Variable named “Path” and add %KAFKA_HOME%\bin\windows
5. Open cmd and enter kafka-server-start.bat %KAFKA_HOME%\config\server.properties to run Kafka server, it will start the Kafka server on port 9092 


##### To experiment on laptop
1. To run ZooKeeper server if not already started, run below command
    * zkserver

2. To run Kafka server if not already started, run below command
    * kafka-server-start.bat %KAFKA_HOME%\config\server.properties

3. Create a Kafka Topic, run below command
    * kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic [TOPIC NAME]
    * Note: replication-factor is 1 because we have only one kafka server running on our laptop

4. Create a Producer for the topic, run below command
    * kafka-console-producer.bat --broker-list localhost:9092 --topic [TOPIC NAME]

5. Create a Consumer for the topic, run below command
    * kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic [TOPIC NAME]
    * Note: By default when you start a consumer, it starts looking for messages that come into the topic after it was created. 
    If you want to view all the messages from the beginning in the topic, try this
        * kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic [TOPIC NAME] --from-beginning

6. You should be able to type messages in Producer and see the messages received in the consumer window

7. Some other useful commands
    * To list all the topics, kafka-topics.bat --list --zookeeper localhost:2181 
    * To describe the topic, kafka-topics.bat --describe --zookeeper localhost:2181 --topic [TOPIC NAME]
    * To delete a topic, kafka-run-class.bat kafka.admin.TopicCommand --delete --topic [TOPIC NAME] --zookeeper localhost:2181
    


#### Project
1. Start ZooKeeper and Kafka Servers
    * Open command prompt and enter zkserver
    * Open command prompt and enter kafka-server-start.bat %KAFKA_HOME%\config\server.properties
2. Create topic test in kafka
    * kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
3. Open the class KafkaPocApplication and run it. Now you have a Producer, and a Consumer listening on topic test
4. Open browser and go to http://localhost:8090/kafka/publish/{message}
5. Logs show a message has been produced and consumed

Ex:
[Producer clientId=producer-1] Cluster ID: tLdfJDpQTJ2LlHG1kD2E9w
Sent message=[ {User{name='Deepak'}} ] with offset=[ {7} ]
$$$$ => Consumed message: User{name='Deepak'}