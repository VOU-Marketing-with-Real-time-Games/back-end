package com.vou.backend.user.dto;

import java.util.Date;

import com.vou.backend.user.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayTurnRequestDto {
    @NotNull(message = "UserID is required")
    long userID;
    @NotNull(message = "Quantity is required")
    int Quantity;
    @NotNull(message = "Method is required")
    String method;
}
