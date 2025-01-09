package com.hcmus.gameservice.quizz.service;

import com.hcmus.gameservice.client.UserClient;
import com.hcmus.gameservice.game_info.exception.QuestionNotFoundException;
import com.hcmus.gameservice.quizz.dto.UserAnswerRequestDto;
import com.hcmus.gameservice.quizz.dto.UserDto;
import com.hcmus.gameservice.quizz.dto.UserQuizzTotalScoreDto;
import com.hcmus.gameservice.quizz.model.Question;
import com.hcmus.gameservice.quizz.model.UserAnswer;
import com.hcmus.gameservice.quizz.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;
    private final int MAX_SCORE = 10;
    private final UserClient userClient;
    public void saveUserAnswer(UserAnswerRequestDto userAnswerRequestDto) throws QuestionNotFoundException {
        UserAnswer userAnswer = modelMapper.map(userAnswerRequestDto, UserAnswer.class);
        Question question = questionService.findQuestionById(userAnswer.getQuestion().getId());
        userAnswer.setQuestion(question);

        int secondsPerQuestion = question.getQuizz().getSecondPerQuestion();

        if (question.getAnswer().equals(userAnswer.getAnswer())) {
            userAnswer.setIsCorrect(true);
            userAnswer.setScore((int) (MAX_SCORE * (1 - (double) (secondsPerQuestion - userAnswer.getAnswerTime()) / secondsPerQuestion)));
        } else {
            userAnswer.setIsCorrect(false);
            userAnswer.setScore(0);
        }
        userAnswerRepository.save(userAnswer);
    }

    public List<UserQuizzTotalScoreDto> totalScoreUserInQuizz(Long quizzId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByQuizzId(quizzId);
        List<UserDto> users = userClient.getUsersByListId(userAnswers.stream().map(UserAnswer::getUserId).toList());
        return users.stream().map(user -> {
            List<UserAnswer> userAnswerList = userAnswers.stream().filter(userAnswer -> userAnswer.getUserId().equals(user.getId())).toList();
            int totalScore = userAnswerList.stream().mapToInt(UserAnswer::getScore).sum();
            return new UserQuizzTotalScoreDto(user.getId(), user.getFullName(), user.getAvatar(), totalScore, quizzId);
        }).toList();
    }
}
