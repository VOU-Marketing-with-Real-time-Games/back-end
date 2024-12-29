package com.vou.backend.game.quizz.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizzTotalScoreDto {
    private Long userId;
    private String fullName;
    private String imageUrl;
    private int totalScore;
    private Long quizzId;
}
