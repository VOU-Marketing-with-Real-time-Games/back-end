package com.hcmus.gameservice.puzzle.repository;

import com.hcmus.gameservice.puzzle.model.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    @Query("select ui from UserItem ui where ui.userId = :userId and ui.item.id = :itemId")
    UserItem findByUserIdAndItemId(Long userId, Long itemId);

    @Query("select ui from UserItem ui where ui.userId = :userId and ui.item.puzzle.id = :puzzleId and ui.totalItem > 0")
    List<UserItem> findByUserIdAndPuzzleId(Long userId, Long puzzleId);

    @Query("select ui from UserItem ui where ui.userId = :userId")
    List<UserItem> findByUserId(Long userId);

}
