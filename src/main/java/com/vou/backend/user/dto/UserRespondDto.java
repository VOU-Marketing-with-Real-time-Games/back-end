package com.vou.backend.user.dto;

import java.util.Date;

import com.vou.backend.user.model.User;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRespondDto {
    private Long id;
    private String avatar;
    private String fullName;
    private String username;
    private String email;
    private String role;
    private String phoneNumber;
    private Date dob;
    private String status;
    private String gender;
    private String facebookLink;
    private Date createdAt;
}
