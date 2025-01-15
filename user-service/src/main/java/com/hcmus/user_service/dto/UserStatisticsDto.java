package com.hcmus.user_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserStatisticsDto {
    private String title;
    private String value;
    private String interval;
    private String trend;
    private List<Integer> data;
}