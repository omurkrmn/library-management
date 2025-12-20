package com.omurkrmn.library_management.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "library.exchange";
    public static final String QUEUE = "library.queue";
    public static final String ROUTING_KEY = "library.routing";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate( connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;

    }
}

