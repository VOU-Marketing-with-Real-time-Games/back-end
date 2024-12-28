package com.vou.backend.game.puzzle.repository;

import com.vou.backend.game.puzzle.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("delete from Item i where i.puzzle.id = :puzzleId")
    @Modifying
    void deleteByPuzzleId(Long puzzleId);

    @Query("select i from Item i where i.puzzle.id = :puzzleId and i.remainingNum > 0")
    List<Item> findByPuzzleId(Long puzzleId);
}
