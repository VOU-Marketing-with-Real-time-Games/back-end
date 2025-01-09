package com.hcmus.gameservice.game_info.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto {
    private Long id;
    private String name;
    private String image;
    private String field;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdAt;
    private  Long brandId;
}
