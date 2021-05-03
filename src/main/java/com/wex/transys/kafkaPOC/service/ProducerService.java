package com.wex.transys.kafkaPOC.service;

import com.wex.transys.kafkaPOC.model.Count;
import com.wex.transys.kafkaPOC.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService
{
    @Autowired
    SimpMessagingTemplate template;

    private final KafkaTemplate<Integer, User> kafkaTemplate;
    private final String TOPIC = "test";

    private int producedCount = 0;

    public ProducerService(KafkaTemplate<Integer, User> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User message)
    {
        System.out.printf("$$$$ => Producer: %s%n", message);

        final int PARTITION_KEY = message.getName().charAt(0) % 2;
        ListenableFuture<SendResult<Integer, User>> future = this.kafkaTemplate.send(TOPIC, PARTITION_KEY, PARTITION_KEY, message);
        template.convertAndSend("/stream/producerCount", new Count(++producedCount));
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
