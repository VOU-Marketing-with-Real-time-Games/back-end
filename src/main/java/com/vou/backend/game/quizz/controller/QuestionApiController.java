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
    public List<QuestionResponseDto> getAllQuestions() {
        return questionService.getAllQuestions().stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable Long id) throws QuestionNotFoundException {
        Question question = questionService.getQuestionById(id);
        QuestionResponseDto questionResponseDto = modelMapper.map(question, QuestionResponseDto.class);
        return ResponseEntity.ok(questionResponseDto);
    }

    @GetMapping("/quizz/{quizzId}")
    public List<QuestionResponseDto> getQuestionsByQuizzId(Long quizzId) {
        return questionService.getQuestionsByQuizzId(quizzId).stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody QuestionRequestDto questionRequestDto) throws QuizzNotFoundException {
        Question question = modelMapper.map(questionRequestDto, Question.class);
        Question createdQuestion = questionService.createQuestion(question);
        QuestionResponseDto questionResponseDto = modelMapper.map(createdQuestion, QuestionResponseDto.class);
        return ResponseEntity.ok(questionResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequestDto questionRequestDto) throws QuizzNotFoundException, QuestionNotFoundException {
        Question questionDetails = modelMapper.map(questionRequestDto, Question.class);
        Question updatedQuestion = questionService.updateQuestion(id, questionDetails);
        QuestionResponseDto questionResponseDto = modelMapper.map(updatedQuestion, QuestionResponseDto.class);
        return ResponseEntity.ok(questionResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
