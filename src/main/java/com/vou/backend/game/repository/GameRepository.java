package com.vou.backend.game.repository;
import com.vou.backend.game.model.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GameRepository extends JpaRepository<GameInfo,Long> {
}
