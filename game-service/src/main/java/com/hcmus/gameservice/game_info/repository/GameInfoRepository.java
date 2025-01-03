package com.hcmus.gameservice.game_info.repository;

import com.hcmus.gameservice.game_info.model.GameInfo;
import com.hcmus.gameservice.game_info.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {
    @Query("SELECT g FROM GameInfo g WHERE g.type = ?1")
    GameInfo findByType(GameType type);
}
