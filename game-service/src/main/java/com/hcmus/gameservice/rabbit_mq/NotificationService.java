package com.hcmus.gameservice.rabbit_mq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public NotificationService(RabbitTemplate rabbitTemplate,
                               @Value("${rabbitmq.exchange}") String exchange,
                               @Value("${rabbitmq.routingkey}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void notifyGameEvent(NotificationDto notificationDto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, notificationDto);
        System.out.println("NotificationDto sent to RabbitMQ: " + notificationDto);
    }
}
