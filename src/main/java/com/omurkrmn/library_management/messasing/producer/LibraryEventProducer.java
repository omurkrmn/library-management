package com.omurkrmn.library_management.messasing.producer;

import com.omurkrmn.library_management.config.RabbitMQConfig;
import com.omurkrmn.library_management.messasing.event.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LibraryEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public LibraryEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(LibraryEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event);
        log.info("EVENT SENT -> {}", event);
    }
}
