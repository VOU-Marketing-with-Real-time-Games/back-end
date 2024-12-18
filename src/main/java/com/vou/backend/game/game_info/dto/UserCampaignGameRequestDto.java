package com.vou.backend.game.game_info.dto;

import lombok.*;

/**
 * Data Transfer Object for UserCampaignGame requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCampaignGameRequestDto {

    /**
     * The ID of the user.
     */
    private Long userId;

    /**
     * The ID of the campaign game.
     */
    private Long campaignGameId;
}