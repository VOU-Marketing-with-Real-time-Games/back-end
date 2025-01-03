package com.hcmus.notificationservice.repository;

import com.hcmus.notificationservice.model.NotificationUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationUser, String> {

    @Query("{ 'userId': ?0, 'isDeleted': false }")
    List<NotificationUser> findByUserId(Long userId);

    @Query("{ 'userId': ?0 }")
    @Update("{ '$set': { 'isDeleted': true } }")
    void softDeleteNotificationByUserId(Long userId);
}
