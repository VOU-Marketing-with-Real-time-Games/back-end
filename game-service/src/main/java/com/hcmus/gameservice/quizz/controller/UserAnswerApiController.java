package com.hcmus.gameservice.quizz.controller;

import com.hcmus.gameservice.game_info.exception.QuestionNotFoundException;
import com.hcmus.gameservice.quizz.dto.UserAnswerRequestDto;
import com.hcmus.gameservice.quizz.service.UserAnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/game/user-answers")
public class UserAnswerApiController {

    @Autowired
    private UserAnswerService userAnswerService;

    @PostMapping
    public ResponseEntity<?> saveUserAnswer(@Valid @RequestBody UserAnswerRequestDto userAnswerRequestDto) throws QuestionNotFoundException {
        userAnswerService.saveUserAnswer(userAnswerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
