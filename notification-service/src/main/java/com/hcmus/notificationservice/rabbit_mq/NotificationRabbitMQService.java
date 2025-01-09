package com.hcmus.notificationservice.rabbit_mq;

import com.hcmus.notificationservice.dto.NotificationDto;
import com.hcmus.notificationservice.socket.NotificationSocketHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
@RequiredArgsConstructor
public class NotificationRabbitMQService {
    private final NotificationSocketHandler notificationSocketHandler;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationRabbitMQService.class);

    @RabbitListener(queues = "game-queue")
    public void handleGameNotification(NotificationDto notificationDto) {
        LOGGER.info("Received from GameService: " + notificationDto);
        try {
            notificationSocketHandler.broadcastToClients(notificationDto);
        } catch (Exception e) {
            LOGGER.error("Error broadcasting notification to clients: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "quizz-queue")
    public void handQuizNotification(NotificationDto notificationDto) {
        LOGGER.info("Received from QuizzService: " + notificationDto);
        try {
            notificationSocketHandler.broadcastToClients(notificationDto);
        } catch (Exception e) {
            LOGGER.error("Error broadcasting notification to clients: " + e.getMessage());
        }
    }
}