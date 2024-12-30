package com.vou.backend.campaign.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFavoriteDto {
    @NotNull(message = "User id value must not be null")
    private Long userId;
    @NotNull(message = "Campaign id value must not be null")
    private Long campaignId;
}
