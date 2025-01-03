package com.hcmus.gameservice.quizz.controller;

import com.hcmus.gameservice.game_info.exception.QuestionNotFoundException;
import com.hcmus.gameservice.game_info.exception.QuizzNotFoundException;
import com.hcmus.gameservice.quizz.dto.QuestionRequestDto;
import com.hcmus.gameservice.quizz.dto.QuestionResponseDto;
import com.hcmus.gameservice.quizz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/game/questions")
@RequiredArgsConstructor
public class QuestionApiController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions(){
        List<QuestionResponseDto> questionResponseDtos = questionService.getAllQuestions();
        return ResponseEntity.ok(questionResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable Long id) throws QuestionNotFoundException {
        QuestionResponseDto questionResponseDto = questionService.getQuestionById(id);
        return ResponseEntity.ok(questionResponseDto);
    }

    @GetMapping("/quizz/{quizzId}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByQuizzId(Long quizzId) {
        List<QuestionResponseDto> questionResponseDtos = questionService.getQuestionsByQuizzId(quizzId);
        return ResponseEntity.ok(questionResponseDtos);
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody QuestionRequestDto questionRequestDto) throws QuizzNotFoundException {
        QuestionResponseDto questionResponseDto = questionService.createQuestion(questionRequestDto);
        return ResponseEntity.ok(questionResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequestDto questionRequestDto) throws QuizzNotFoundException, QuestionNotFoundException {
        QuestionResponseDto questionResponseDto = questionService.updateQuestion(id, questionRequestDto);
        return ResponseEntity.ok(questionResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
