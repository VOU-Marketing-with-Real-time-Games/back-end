package com.vou.backend.notification.model;
import com.vou.backend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Table(name = "notification_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date time;
    private Boolean isRead;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
