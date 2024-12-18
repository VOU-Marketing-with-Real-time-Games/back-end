package com.vou.backend.game.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseDto {
    private Long id;
    private String questionName;
    private String image;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String explaination;
    private Long quizzId;
}
