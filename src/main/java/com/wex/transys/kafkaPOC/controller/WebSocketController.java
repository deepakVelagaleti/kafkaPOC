package com.wex.transys.kafkaPOC.controller;

import com.wex.transys.kafkaPOC.model.User;
import com.wex.transys.kafkaPOC.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController
{
    @Autowired
    ProducerService producerService;

    @MessageMapping("/toKafka")
//    @SendTo("/stream/consumer1") -- uncomment this to send to websocket, poc is to send message to kafka topic
    public User getMessage(User message)
    {
        producerService.sendMessage(message);
        return new User(message.getName());
    }
}
