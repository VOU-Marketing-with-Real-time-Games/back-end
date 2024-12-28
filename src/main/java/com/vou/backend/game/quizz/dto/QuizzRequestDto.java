package com.vou.backend.game.quizz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizzRequestDto {
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    private String name;

    private String description;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer secondPerQuestion;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long campaignGameId;
}
