package com.hcmus.gameservice.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDto {
    private QuizzResponseDto quizzDto;
    private List<QuestionResponseDto> questionDtos;
}
