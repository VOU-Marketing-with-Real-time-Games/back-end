package com.hcmus.campaignservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampaignDailyCountDto {
    private int totalCampaign;
    private String date;
}