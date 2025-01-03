package com.hcmus.gameservice.game_info.repository;

import com.hcmus.gameservice.game_info.model.GameCampaign;
import com.hcmus.gameservice.game_info.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCampaignRepository extends JpaRepository<GameCampaign, Long> {
    @Query("SELECT g FROM GameCampaign g WHERE g.campaignId = ?1")
    List<GameCampaign> findByCampaignId(Long campaignId);

    @Query("SELECT DISTINCT g.campaignId FROM GameCampaign g where g.gameInfo.id = ?1")
    List<Long> findDistinctCampaignByGameId(Long gameId);

    @Query("SELECT g FROM GameCampaign g WHERE g.gameInfo.type = ?1 AND g.gameId = ?2")
    GameCampaign findByGameTypeAndGameId(GameType gameType, Long gameId);
}
