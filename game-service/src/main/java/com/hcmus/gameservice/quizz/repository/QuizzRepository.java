package com.hcmus.gameservice.quizz.repository;

import com.hcmus.gameservice.quizz.model.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Long> {
}
