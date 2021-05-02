package com.wex.transys.kafkaPOC.service;

import com.wex.transys.kafkaPOC.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService
{
    private final KafkaTemplate<Integer, User> kafkaTemplate;
    private final String TOPIC = "test";

    public ProducerService(KafkaTemplate<Integer, User> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User message)
    {
        System.out.println(String.format("$$$$ => Producer: %s", message));

        final int PARTITION_KEY = message.getName().charAt(0) % 2;
        ListenableFuture<SendResult<Integer, User>> future = this.kafkaTemplate.send(TOPIC, PARTITION_KEY, PARTITION_KEY, message);
        future.addCallback(new ListenableFutureCallback<>()
        {
            @Override
            public void onFailure(Throwable ex)
            {
                System.out.printf("Unable to send message=[ {%s} ] due to : {%s}%n", message, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Integer, User> result)
            {
//                System.out.println(String.format("Sent message=[ {%s} ] with offset=[ {%s} ]", message, result.getRecordMetadata().offset()));
            }
        });
    }
}
