package com.vou.backend.notification.repository;

import com.vou.backend.notification.model.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationUser,Long> {
}
