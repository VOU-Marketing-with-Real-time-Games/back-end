package com.vou.backend.game.quizz.service;

import com.vou.backend.game.game_info.exception.QuestionNotFoundException;
import com.vou.backend.game.game_info.exception.QuizzNotFoundException;
import com.vou.backend.game.quizz.dto.QuestionRequestDto;
import com.vou.backend.game.quizz.dto.QuestionResponseDto;
import com.vou.backend.game.quizz.model.Question;
import com.vou.backend.game.quizz.model.Quizz;
import com.vou.backend.game.quizz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizzService quizzService;
    private final ModelMapper modelMapper;

    public List<QuestionResponseDto> getAllQuestions() {
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
        Quizz quizz = quizzService.findQuizzById(question.getQuizz().getId());
        question.setQuizz(quizz);
        Question createdQuestion = questionRepository.save(question);
        return modelMapper.map(createdQuestion, QuestionResponseDto.class);
    }

    public QuestionResponseDto updateQuestion(Long id, QuestionRequestDto questionRequestDto) throws QuestionNotFoundException, QuizzNotFoundException {
        Question questionDetails = modelMapper.map(questionRequestDto, Question.class);
        questionDetails.setQuizz(quizzService.findQuizzById(questionDetails.getQuizz().getId()));
        Question question = findQuestionById(id);
        question.copy(questionDetails);
        Question updatedQuestion = questionRepository.save(question);
        return modelMapper.map(updatedQuestion, QuestionResponseDto.class);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
