package com.vou.backend.user.model;

import com.vou.backend.campaign.model.Campaign;
import com.vou.backend.campaign.model.CampaignGame;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_campaign_game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCampaignGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "campaign_game_id")
    private CampaignGame campaignGame;
    private Boolean isCompleted;
}
