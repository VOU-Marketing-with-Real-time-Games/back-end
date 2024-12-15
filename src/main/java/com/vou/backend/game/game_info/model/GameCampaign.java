package com.vou.backend.game.game_info.model;

import com.vou.backend.campaign.model.Campaign;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long campaignId;

    @ManyToOne
    @JoinColumn(name = "game_info_id")
    private GameInfo gameInfo;

    private Long gameId;
}
