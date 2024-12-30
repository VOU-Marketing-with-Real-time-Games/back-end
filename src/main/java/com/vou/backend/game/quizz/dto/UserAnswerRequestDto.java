package com.vou.backend.game.quizz.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAnswerRequestDto {
    private Long userId;
    private Long questionId;
    private String answer;
    private Integer answerTime;
}
