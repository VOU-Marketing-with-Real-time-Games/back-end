package com.vou.backend.user.model;

import com.vou.backend.notification.model.NotificationUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String status;
    private String phoneNumber;
    private String username;
    private Date dob;
    private String gender;
    private String facebookLink;
    private Integer turnNum;
    private Date createdAt;

    public void copy(User user) {
        // Copy properties that are not null
        if (user.getAvatar() != null) {
            this.setAvatar(user.getAvatar());
        }
        if (user.getFullName() != null) {
            this.setFullName(user.getFullName());
        }
        if (user.getEmail() != null) {
            this.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            this.setPassword(user.getPassword());
        }
        if (user.getRole() != null) {
            this.setRole(user.getRole());
        }
        if (user.getStatus() != null) {
            this.setStatus(user.getStatus());
        }
        if (user.getPhoneNumber() != null) {
            this.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getUsername() != null) {
            this.setUsername(user.getUsername());
        }
        if (user.getDob() != null) {
            this.setDob(user.getDob());
        }
        if (user.getGender() != null) {
            this.setGender(user.getGender());
        }
        if (user.getFacebookLink() != null) {
            this.setFacebookLink(user.getFacebookLink());
        }
    }
}
