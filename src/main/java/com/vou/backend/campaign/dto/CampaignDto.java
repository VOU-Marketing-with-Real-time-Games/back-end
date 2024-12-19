package com.vou.backend.campaign.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignDto {
    @NotBlank(message = "Name of campaign must not be empty")
    private String name;
    @NotBlank(message = "Image of campaign must not be empty")
    private String image;
    @NotBlank(message = "Filed ID of campaign must not be empty")
    private String filedId;
    @NotNull(message = "Start date of campaign must not be null")
    private Date startDate;
    @NotNull(message = "End date of campaign must not be null")
    private Date endDate;
}
