package com.hcmus.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notification_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationUser {
    @Id
    private String id;
    private String content;
    private Date createdAt;
    private Boolean isRead;
    private Boolean isDeleted;
    private Long userId;
}