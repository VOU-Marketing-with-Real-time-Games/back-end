package com.hcmus.quizzrealtime.controller;

import com.hcmus.quizzrealtime.dto.QuestionDto;
import com.hcmus.quizzrealtime.dto.QuizzDto;
import com.hcmus.quizzrealtime.dto.ScheduleRequestDto;
import com.hcmus.quizzrealtime.service.TaskSchedulerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v3/quizz-realtime")
public class QuizzRealtimeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuizzRealtimeController.class);
    private final TaskSchedulerService taskSchedulerService;

    @PostMapping("/schedule")
    public boolean scheduleQuiz(@RequestBody ScheduleRequestDto request) {
        try {
            taskSchedulerService.scheduleQuizStart(request.getQuizzDto(), request.getQuestionDtos());
            return true;
        } catch (Exception e) {
            LOGGER.error("Error scheduling quiz: {}", e.getMessage());
            return false;
        }
    }
}
