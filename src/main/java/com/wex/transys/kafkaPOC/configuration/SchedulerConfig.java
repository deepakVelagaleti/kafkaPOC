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

    private static final String EVEN_NUMBERS = "02468";
    private static final String ODD_NUMBERS = "13579";

    private boolean prevKeyWasEven = false;

    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessages()
    {
        User message = new User(getRandomString());
        producerService.sendMessage(message);
    }

    private String getRandomString()
    {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890~!@#$%^&*(){}[]+-:;<>,.?";
        char firstChar = prevKeyWasEven ? getFirstChar(ODD_NUMBERS) : getFirstChar(EVEN_NUMBERS);
        StringBuilder randomStr = new StringBuilder(firstChar+"");
        Random rnd = new Random();
        while (randomStr.length() < 6)   // length of the random string.
        {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            randomStr.append(CHARS.charAt(index));
        }
        return randomStr.toString();
    }


    private char getFirstChar(String NUMBERS)
    {
        Random rnd = new Random();
        int index = (int) (rnd.nextFloat() * NUMBERS.length());

        prevKeyWasEven = !prevKeyWasEven;

        return NUMBERS.charAt(index);
    }
}