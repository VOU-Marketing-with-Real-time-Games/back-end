package com.hcmus.auth_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull(message = "username is required")
    @NotEmpty(message = "username cannot be empty")
    private String username;
    @NotNull(message = "password is required")
    @NotEmpty(message = "password cannot be empty")
    private String password;
}
