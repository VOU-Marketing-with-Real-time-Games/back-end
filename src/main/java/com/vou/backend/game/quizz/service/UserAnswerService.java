package com.vou.backend.game.quizz.service;

import com.vou.backend.game.game_info.exception.QuestionNotFoundException;
import com.vou.backend.game.quizz.dto.QuestionResponseDto;
import com.vou.backend.game.quizz.dto.UserAnswerRequestDto;
import com.vou.backend.game.quizz.dto.UserQuizzTotalScoreDto;
import com.vou.backend.game.quizz.model.Question;
import com.vou.backend.game.quizz.model.UserAnswer;
import com.vou.backend.game.quizz.repository.UserAnswerRepository;
import com.vou.backend.user.model.User;
import com.vou.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.UserDatabase;
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
    private final UserService userService;

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
        List<User> users = userService.findByListId(userAnswers.stream().map(UserAnswer::getUserId).toList());
        return users.stream().map(user -> {
            List<UserAnswer> userAnswerList = userAnswers.stream().filter(userAnswer -> userAnswer.getUserId().equals(user.getId())).toList();
            int totalScore = userAnswerList.stream().mapToInt(UserAnswer::getScore).sum();
            return new UserQuizzTotalScoreDto(user.getId(), user.getFullName(), user.getAvatar() ,totalScore, quizzId);
        }).toList();
    }
}
