package com.vou.backend.game.game_info.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for GameCampaign responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCampaignResponseDto {

    /**
     * The ID of the game campaign.
     */
    private Long id;

    /**
     * The ID of the campaign.
     */
    private Long campaignId;

    /**
     * The game information associated with the campaign.
     */
    private GameInfoDto gameInfodto;

    /**
     * The ID of the game.
     */
    private Long gameId;
}