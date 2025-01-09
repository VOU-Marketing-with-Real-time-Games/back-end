package com.hcmus.gameservice.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
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
