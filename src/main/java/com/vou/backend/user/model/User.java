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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public Integer getTurnNum() {
        return turnNum;
    }

    public void setTurnNum(Integer turnNum) {
        this.turnNum = turnNum;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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
