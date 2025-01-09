package com.hcmus.user_service.repository;

import com.hcmus.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    User findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.id IN :listId")
    List<User> findByIds(List<Long> listId);
}
