package com.vou.backend.filter.game.puzzle.repository;

import com.vou.backend.filter.game.puzzle.model.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle,Long> {
}
