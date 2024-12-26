package com.vou.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDto {
    private String avatar;
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    private String fullName;
    @NotNull(message = "Username is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username can only contain alphanumeric characters")
    private String userName;
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Role is required")
    private String role;
    @NotNull(message = "PhoneNumber is required")
    private String phoneNumber;
    private Date dob;
    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Gender must be 'MALE' or 'FEMALE'")
    private String gender;
    private String facebookLink;
}
