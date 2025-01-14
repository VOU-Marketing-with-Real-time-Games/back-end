package com.hcmus.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResetPasswordDto {
    private String password;
    private String refreshToken;
}
