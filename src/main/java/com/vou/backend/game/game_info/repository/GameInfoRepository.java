package com.vou.backend.game.game_info.repository;

import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {
    @Query("SELECT g FROM GameInfo g WHERE g.type = ?1")
    GameInfo findByType(GameType type);
}
