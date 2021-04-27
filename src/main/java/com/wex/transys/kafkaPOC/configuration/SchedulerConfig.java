package com.wex.transys.kafkaPOC.configuration;

import com.wex.transys.kafkaPOC.model.User;
import com.wex.transys.kafkaPOC.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@EnableScheduling
@Configuration
public class SchedulerConfig
{
    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    ProducerService producerService;

    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessages()
    {
        template.convertAndSend("/topic/user", new User("Fixed Delay Scheduler"));
        producerService.sendMessage(new User(getRandomString()));
    }

    private String getRandomString()
    {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder randomStr = new StringBuilder();
        Random rnd = new Random();
        while (randomStr.length() < 6)   // length of the random string.
        {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            randomStr.append(CHARS.charAt(index));
        }
        return randomStr.toString();
    }
}