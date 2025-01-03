package com.hcmus.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object for Notification.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private String id;
    private String content;
    private Date createdAt;
    private Boolean isRead;
    private Long userId;
}