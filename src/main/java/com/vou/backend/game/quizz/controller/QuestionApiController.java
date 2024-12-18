package com.vou.backend.game.quizz.controller;

import com.vou.backend.game.game_info.exception.QuestionNotFoundException;
import com.vou.backend.game.game_info.exception.QuizzNotFoundException;
import com.vou.backend.game.quizz.dto.QuestionRequestDto;
import com.vou.backend.game.quizz.dto.QuestionResponseDto;
import com.vou.backend.game.quizz.model.Question;
import com.vou.backend.game.quizz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionApiController {

    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
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
