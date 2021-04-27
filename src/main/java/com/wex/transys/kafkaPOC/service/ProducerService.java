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
    private final KafkaTemplate<String, User> kafkaTemplate;
    private final String TOPIC = "test";

    public ProducerService(KafkaTemplate<String, User> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User message)
    {
        System.out.println(String.format("$$$$ => Producing message: %s", message));

        ListenableFuture<SendResult<String, User>> future = this.kafkaTemplate.send(TOPIC, message);
        future.addCallback(new ListenableFutureCallback<>()
        {
            @Override
            public void onFailure(Throwable ex)
            {
                System.out.printf("Unable to send message=[ {%s} ] due to : {%s}%n", message, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, User> result)
            {
//                System.out.println(String.format("Sent message=[ {%s} ] with offset=[ {%s} ]", message, result.getRecordMetadata().offset()));
            }
        });
    }
}
