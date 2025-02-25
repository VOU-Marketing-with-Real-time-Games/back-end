package com.hcmus.notificationservice.rabbit_mq;

import com.hcmus.notificationservice.client.UserClient;
import com.hcmus.notificationservice.dto.NotificationDto;
import com.hcmus.notificationservice.dto.UserRespondDto;
import com.hcmus.notificationservice.model.NotificationUser;
import com.hcmus.notificationservice.repository.NotificationRepository;
import com.hcmus.notificationservice.socket.NotificationSocketHandler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationRabbitMQService {
    private final NotificationSocketHandler notificationSocketHandler;
    private UserClient userClient;
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
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

    @RabbitListener(queues = "campaign-queue")
    public void handleCampaignNotification(NotificationDto notificationDto) {
        LOGGER.info("Received from CampaignService: " + notificationDto);
        try {
            List<UserRespondDto> adminUsers = userClient.getAdmins();
            for (UserRespondDto admin : adminUsers) {
                NotificationUser notificationUser = modelMapper.map(notificationDto, NotificationUser.class);
                notificationUser.setUserId(admin.getId());
                notificationUser.setIsDeleted(false);
                notificationUser.setCreatedAt(new Date());
                notificationRepository.save(notificationUser);
                NotificationDto response = modelMapper.map(notificationUser, NotificationDto.class);
                notificationSocketHandler.broadcastToClients(response);
            }
        } catch (Exception e) {
            LOGGER.error("Error broadcasting notification to clients: " + e.getMessage());
        }
    }
}