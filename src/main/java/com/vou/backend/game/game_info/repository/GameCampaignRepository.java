package com.vou.backend.game.game_info.repository;

import com.vou.backend.game.game_info.model.GameCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCampaignRepository extends JpaRepository<GameCampaign, Long> {
    @Query("SELECT g FROM GameCampaign g WHERE g.campaignId = ?1")
    List<GameCampaign> findByCampaignId(Long campaignId);
}
