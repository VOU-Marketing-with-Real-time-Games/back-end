package com.vou.backend.game.game_info.model;

import com.vou.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_campaign_game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
