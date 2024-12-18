package com.vou.backend.game.game_info.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

/**
 * Data Transfer Object for GameCampaign requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameCampaignRequestDto {

    /**
     * The ID of the campaign.
     * Cannot be null.
     */
    @NotNull(message = "Campaign id cannot be null")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long campaignId;

    /**
     * The ID of the game information.
     * Cannot be null.
     */
    @NotNull(message = "Game info id cannot be null")
    private Long gameInfoId;

    /**
     * The ID of the game.
     */
    private Long gameId;
}