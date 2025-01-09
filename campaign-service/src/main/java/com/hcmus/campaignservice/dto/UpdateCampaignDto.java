package com.hcmus.campaignservice.dto;

import jakarta.validation.constraints.Future;
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
public class UpdateCampaignDto {
    @NotBlank(message = "Name of campaign must not be empty")
    private String name;
    @NotBlank(message = "Image of campaign must not be empty")
    private String image;
    @NotBlank(message = "Field of campaign must not be empty")
    private String field;
    @NotNull(message = "Start date of campaign must not be null")
    @Future(message = "Start date must be in the future")
    private Date startDate;
    @NotNull(message = "End date of campaign must not be null")
    @Future(message = "End date must be in the future")
    private Date endDate;
    @NotNull(message = "Brand id value must not be null")
    private  Long brandId;
    private String status;
}
