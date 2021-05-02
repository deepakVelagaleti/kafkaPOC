package com.wex.transys.kafkaPOC.service;

import com.wex.transys.kafkaPOC.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService
{
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "test", partitions = {"0"})}, groupId = "group_idA",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeA0(User message)
    {
        template.convertAndSend("/stream/consumerA0", message);
        System.out.printf("$$$$ => Consumer-A0: %s%n", message);
    }

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "test", partitions = {"1"})}, groupId = "group_idA",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeA1(User message)
    {
        template.convertAndSend("/stream/consumerA1", message);
        System.out.printf("$$$$ => Consumer-A1: %s%n", message);
    }

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "test", partitions = {"0"})}, groupId = "group_idB",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeB0(User message)
    {
        template.convertAndSend("/stream/consumerB0", message);
        System.out.printf("$$$$ => Consumer-B0: %s%n", message);
    }

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "test", partitions = {"1"})}, groupId = "group_idB",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeB1(User message)
    {
        template.convertAndSend("/stream/consumerB1", message);
        System.out.printf("$$$$ => Consumer-B1: %s%n", message);
    }
}
