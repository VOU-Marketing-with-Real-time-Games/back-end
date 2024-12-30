package com.vou.backend.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RedeemGiftRequest {
    @NotNull(message = "token is required")
    private String token;
    @NotNull(message = "receiverId is required")
    private Long receiverId;
}
