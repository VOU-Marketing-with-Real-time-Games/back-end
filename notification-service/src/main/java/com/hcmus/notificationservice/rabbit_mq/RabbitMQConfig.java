package com.hcmus.notificationservice.rabbit_mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    // GameService Configuration
    @Bean
    public Queue gameQueue() {
        return new Queue("game-queue", true);
    }

    @Bean
    public DirectExchange gameExchange() {
        return new DirectExchange("game-exchange");
    }

    @Bean
    public Binding gameBinding(Queue gameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(gameQueue).to(gameExchange).with("game-routing");
    }

    // Global Message Converter
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
