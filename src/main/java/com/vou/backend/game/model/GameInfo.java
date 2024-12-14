package com.vou.backend.game.model;

import com.vou.backend.campaign.model.CampaignGame;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private GameType type;
    private String manual;
    private Boolean enable;
    @OneToMany(mappedBy = "gameInfo", cascade = CascadeType.ALL)
    private List<CampaignGame> campaignGames = new ArrayList<>();
}
