package com.vou.backend.game.quizz.repository;

import com.vou.backend.game.quizz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.quizz.id = ?1")
    List<Question> findAllByQuizzId(Long quizzId);
}
