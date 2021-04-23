package com.wex.transys.kafkaPOC.controller;

import com.wex.transys.kafkaPOC.model.User;
import com.wex.transys.kafkaPOC.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController
{
    private final ProducerService producerService;

    @Autowired
    public KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping(value = "/publish/{name}")
    public void sendMessageToKafkaTopic(@PathVariable String name)
    {
        producerService.sendMessage(new User(name));
    }
}
