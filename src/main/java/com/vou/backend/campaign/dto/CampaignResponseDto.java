package com.vou.backend.campaign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignResponseDto {
    private Long id;
    private String name;
    private String image;
    private String filedId;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdAt;
}
