package com.hcmus.gameservice.quizz.service;

import com.hcmus.gameservice.game_info.exception.QuestionNotFoundException;
import com.hcmus.gameservice.game_info.exception.QuizzNotFoundException;
import com.hcmus.gameservice.quizz.dto.QuestionRequestDto;
import com.hcmus.gameservice.quizz.dto.QuestionResponseDto;
import com.hcmus.gameservice.quizz.model.Question;
import com.hcmus.gameservice.quizz.model.Quizz;
import com.hcmus.gameservice.quizz.repository.QuestionRepository;
import com.hcmus.gameservice.quizz.repository.QuizzRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizzRepository quizzRepository;
    private final ModelMapper modelMapper;

    public List<QuestionResponseDto> getAllQuestions(){
        return questionRepository.findAll().stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .collect(Collectors.toList());
    }

    public QuestionResponseDto getQuestionById(Long id) throws QuestionNotFoundException {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found with id " + id));
        return modelMapper.map(question, QuestionResponseDto.class);
    }

    public Question findQuestionById(Long id) throws QuestionNotFoundException {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found with id " + id));
    }

    public List<QuestionResponseDto> getQuestionsByQuizzId(Long quizzId) {
        return questionRepository.findAllByQuizzId(quizzId).stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .collect(Collectors.toList());
    }

    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto) throws QuizzNotFoundException {
        Question question = modelMapper.map(questionRequestDto, Question.class);
        System.out.println(question);
        Quizz quizz = quizzRepository.findById(question.getQuizz().getId()).orElseThrow(() -> new QuizzNotFoundException("Quizz not found with id " + question.getQuizz().getId()));
        question.setQuizz(quizz);
        Question createdQuestion = questionRepository.save(question);
        return modelMapper.map(createdQuestion, QuestionResponseDto.class);
    }

    public QuestionResponseDto updateQuestion(Long id, QuestionRequestDto questionRequestDto) throws QuestionNotFoundException, QuizzNotFoundException {
        Question questionDetails = modelMapper.map(questionRequestDto, Question.class);
        questionDetails.setQuizz(quizzRepository.findById(questionDetails.getQuizz().getId()).orElseThrow(() -> new QuizzNotFoundException("Quizz not found with id " + questionDetails.getQuizz().getId())));
        Question question = findQuestionById(id);
        question.copy(questionDetails);
        Question updatedQuestion = questionRepository.save(question);
        return modelMapper.map(updatedQuestion, QuestionResponseDto.class);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
