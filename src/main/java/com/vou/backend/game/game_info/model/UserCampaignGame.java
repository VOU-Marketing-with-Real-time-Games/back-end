package com.vou.backend.game.game_info.model;

import com.vou.backend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "campaign_game_id")
    private GameCampaign campaignGame;

    private Boolean isCompleted;
}
