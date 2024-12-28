package com.vou.backend.notification.controller;

import com.vou.backend.notification.dto.NotificationDto;
import com.vou.backend.notification.exception.NotificationUserNotFoundException;
import com.vou.backend.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    /**
     * Adds a new notification.
     *
     * @param notificationDto the notification to add
     * @return the added notification
     */
    @PostMapping
    public ResponseEntity<NotificationDto> addNotification(@RequestBody NotificationDto notificationDto) throws Exception {
        NotificationDto addedNotification = notificationService.addNotification(notificationDto);
        return new ResponseEntity<>(addedNotification, HttpStatus.CREATED);
    }


    /**
     * Soft deletes a notification by setting its isDeleted flag to true.
     *
     * @param id the ID of the notification to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteNotification(@PathVariable Long id) throws NotificationUserNotFoundException {
        notificationService.softDeleteNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Soft deletes all notifications for a user by setting their isDeleted flag to true.
     *
     * @param userId the ID of the user whose notifications to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> softDeleteNotificationByUserId(@PathVariable Long userId) {
        notificationService.softDeleteNotificationByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of notifications for the specified user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUser(@PathVariable Long userId) {
        List<NotificationDto> notifications = notificationService.getNotificationByUser(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}
