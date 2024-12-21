package com.vou.backend.user.dto;

import java.util.Date;

import com.vou.backend.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRespondDto {
    private Long id;
    private String avatar;
    private String fullName;
    private String username;
    private String email;
    private String role;
    private String phoneNumber;
    private Date dob;
    private String gender;
    private String facebookLink;
    private Date createdAt;
}
