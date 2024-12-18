package com.vou.backend.game.game_info.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for UserCampaignGame responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCampaignGameResponseDto {

    /**
     * The ID of the user campaign game.
     */
    private Long id;

    /**
     * The ID of the user.
     */
    private Long userId;

    /**
     * The ID of the campaign game.
     */
    private Long campaignGameId;

    /**
     * Indicates whether the user completed the campaign game
     */
    private Boolean isCompleted;
}