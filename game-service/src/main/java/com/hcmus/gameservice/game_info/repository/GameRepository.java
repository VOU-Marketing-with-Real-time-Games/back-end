package com.hcmus.gameservice.game_info.repository;

import com.hcmus.gameservice.game_info.model.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameInfo, Long> {
}
