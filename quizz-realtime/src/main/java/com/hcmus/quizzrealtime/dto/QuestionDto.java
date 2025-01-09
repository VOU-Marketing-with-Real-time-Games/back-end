package com.hcmus.quizzrealtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
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
