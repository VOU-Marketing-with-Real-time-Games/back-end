package com.vou.backend.game.quizz.repository;

import com.vou.backend.game.quizz.model.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Long> {
}
