package com.hcmus.gameservice.quizz.repository;

import com.hcmus.gameservice.quizz.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    @Query("SELECT ua from UserAnswer ua WHERE ua.question.quizz.id = :quizzId")
    List<UserAnswer> findByQuizzId(Long quizzId);
}
