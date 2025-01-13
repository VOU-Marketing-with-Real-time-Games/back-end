package com.hcmus.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserUpdateDto {
    private String avatar;
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    private String fullName;
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Role is required")
    private String role;
    @NotNull(message = "PhoneNumber is required")
    private String phoneNumber;
    @Pattern(regexp = "^(ACTIVE|INACTIVE|BANNED)$", message = "Status must be 'ACTIVE' or 'INACTIVE' or 'BANNED'")
    private String status;
    private String password;
}