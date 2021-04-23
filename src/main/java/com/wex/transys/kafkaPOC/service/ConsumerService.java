package com.wex.transys.kafkaPOC.service;

import com.wex.transys.kafkaPOC.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService
{
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = "test", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void consume1(User user)
    {
        template.convertAndSend("/topic/user", user);
        System.out.println(String.format("$$$$ => Consumed message: %s", user));
    }
}