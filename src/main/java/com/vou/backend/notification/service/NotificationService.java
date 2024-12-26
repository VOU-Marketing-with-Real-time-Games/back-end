package com.vou.backend.notification.service;

import com.vou.backend.notification.dto.NotificationDto;
import com.vou.backend.notification.exception.NotificationUserNotFoundException;
import com.vou.backend.notification.model.NotificationUser;
import com.vou.backend.notification.repository.NotificationRepository;
import com.vou.backend.notification.socket.NotificationSocketHandler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final NotificationSocketHandler notificationSocketHandler;

    /**
     * Adds a new notification.
     *
     * @param notificationDto the notification to add
     * @return the added notification
     */
    public NotificationDto addNotification(NotificationDto notificationDto) throws Exception {
        NotificationUser notificationUser = modelMapper.map(notificationDto, NotificationUser.class);
        notificationUser.setIsDeleted(false);
        notificationUser.setCreatedAt(new Date());
        notificationUser.setIsRead(false);
        NotificationUser savedNotification = notificationRepository.save(notificationUser);
        NotificationDto response = modelMapper.map(savedNotification, NotificationDto.class);
        notificationSocketHandler.broadcastToClients(response);
        return response;
    }

    /**
     * Soft deletes a notification by setting its isDeleted flag to true.
     *
     * @param id the ID of the notification to delete
     * @throws IllegalArgumentException if the notification is not found
     */
    public void softDeleteNotification(Long id) throws NotificationUserNotFoundException {
        NotificationUser notificationUser = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationUserNotFoundException("Notification not found with id: " + id));
        notificationUser.setIsDeleted(true);
        notificationRepository.save(notificationUser);
    }

    /**
     * Soft deletes all notifications for a user by setting their isDeleted flag to true.
     *
     * @param userId the ID of the user whose notifications to delete
     * @throws IllegalArgumentException if the user is not found
     */
    public void softDeleteNotificationByUserId(Long userId){
        notificationRepository.softDeleteNotificationByUserId(userId);
    }

    /**
     * Retrieves all notifications.
     *
     * @return a list of all notifications
     */
    public List<NotificationDto> getNotificationByUser(Long userId) {
        List<NotificationUser> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(notificationUser -> modelMapper.map(notificationUser, NotificationDto.class))
                .collect(Collectors.toList());
    }
}
