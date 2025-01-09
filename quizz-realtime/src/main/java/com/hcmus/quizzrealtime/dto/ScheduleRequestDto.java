package com.hcmus.quizzrealtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDto {
    private QuizzDto quizzDto;
    private List<QuestionDto> questionDtos;
}
