package com.hcmus.brandservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandDailyCountDto {
    private int totalBrand;
    private String date;
}