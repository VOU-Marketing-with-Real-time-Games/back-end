package com.vou.backend.game.quizz.service;

import com.vou.backend.game.game_info.exception.QuestionNotFoundException;
import com.vou.backend.game.game_info.exception.QuizzNotFoundException;
import com.vou.backend.game.quizz.model.Question;
import com.vou.backend.game.quizz.model.Quizz;
import com.vou.backend.game.quizz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizzService quizzService;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) throws QuestionNotFoundException {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found with id " + id));
    }

    public List<Question> getQuestionsByQuizzId(Long quizzId) {
        return questionRepository.findAllByQuizzId(quizzId);
    }

    public Question createQuestion(Question question) throws QuizzNotFoundException {
        Quizz quizz = quizzService.getQuizzById(question.getQuizz().getId());
        question.setQuizz(quizz);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question questionDetails) throws QuestionNotFoundException, QuizzNotFoundException {
        questionDetails.setQuizz(quizzService.getQuizzById(questionDetails.getQuizz().getId()));
        Question question = getQuestionById(id);
        question.copy(questionDetails);
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
