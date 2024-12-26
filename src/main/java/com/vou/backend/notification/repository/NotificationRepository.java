package com.vou.backend.notification.repository;

import com.vou.backend.notification.model.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationUser,Long> {
    @Query("SELECT n FROM NotificationUser n WHERE n.userId = :userId AND n.isDeleted = false")
    List<NotificationUser> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE NotificationUser n SET n.isDeleted = true WHERE n.userId = :userId")
    void softDeleteNotificationByUserId(Long userId);
}
