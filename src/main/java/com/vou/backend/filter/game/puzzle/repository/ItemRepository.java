package com.vou.backend.filter.game.puzzle.repository;

import com.vou.backend.filter.game.puzzle.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("delete from Item i where i.puzzle.id = :puzzleId")
    @Modifying
    void deleteByPuzzleId(Long puzzleId);
}
