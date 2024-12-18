package com.vou.backend.game.game_info.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "game_type")
    @Enumerated(EnumType.STRING)
    private GameType type;

    private String manual;
    private Boolean enable;

    public void copy(GameInfo gameInfo) {
        this.name = gameInfo.getName();
        this.image = gameInfo.getImage();
        this.manual = gameInfo.getManual();
        this.enable = gameInfo.getEnable();
    }
}
