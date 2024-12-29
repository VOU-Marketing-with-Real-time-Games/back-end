package com.vou.backend.game.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizzResponseDto {
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private Integer secondPerQuestion;
    private String startTime;
    private Long campaignGameId;
}
