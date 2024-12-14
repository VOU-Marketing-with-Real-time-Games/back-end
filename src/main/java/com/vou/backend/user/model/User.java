package com.vou.backend.user.model;

import com.vou.backend.notification.model.NotificationUser;
import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<NotificationUser> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserItem> userItems = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserFavoriteCampaign> favouriteCampaigns = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserVoucher>  userVouchers = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCampaignGame> userCampaignGames = new ArrayList<>();
}