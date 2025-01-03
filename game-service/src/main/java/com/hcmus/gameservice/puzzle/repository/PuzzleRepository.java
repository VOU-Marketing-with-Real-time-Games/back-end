package com.hcmus.gameservice.puzzle.repository;

import com.hcmus.gameservice.puzzle.model.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle,Long> {
}
