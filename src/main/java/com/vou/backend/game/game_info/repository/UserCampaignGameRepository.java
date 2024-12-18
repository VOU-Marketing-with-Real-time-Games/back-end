package com.vou.backend.game.game_info.repository;

import com.vou.backend.game.game_info.model.UserCampaignGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCampaignGameRepository extends JpaRepository<UserCampaignGame,Long> {
    @Query("SELECT u FROM UserCampaignGame u WHERE u.userId = ?1")
    List<UserCampaignGame> findByUserId(Long userId);

    @Query("SELECT u FROM UserCampaignGame u WHERE u.userId = ?1 AND u.campaignGame.id = ?2")
    UserCampaignGame findByUserIdAndCampaignGameId(Long userId, Long campaignGameId);
}
