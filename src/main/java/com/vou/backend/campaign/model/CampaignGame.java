package com.vou.backend.campaign.model;

import com.vou.backend.game.model.GameInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "campaign_game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "game_info_id")
    private GameInfo gameInfo;

}
