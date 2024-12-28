package com.vou.backend.game.game_info.repository;

import com.vou.backend.game.game_info.model.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameInfo, Long> {
}
