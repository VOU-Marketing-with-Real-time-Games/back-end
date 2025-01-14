package com.hcmus.brandservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BrandStatisticsDto {
    private String title;
    private String value;
    private String interval;
    private String trend;
    private List<Integer> data;
}