package com.hcmus.gameservice.game_info.repository;

import com.hcmus.gameservice.game_info.model.UserCampaignGame;
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

    @Query("SELECT DISTINCT u.userId FROM UserCampaignGame u WHERE u.campaignGame.id = ?1")
    List<Long> findDistinctUserIdByCampaignId(Long campaignId);

    @Query("SELECT DISTINCT u.userId FROM UserCampaignGame u")
    List<Long> findDistinctUserIdAllCampaign();
}
