package com.vou.backend.game.puzzle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTradeDto {
    private Long itemId;
    private Long userId;

    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int numItem;
}
