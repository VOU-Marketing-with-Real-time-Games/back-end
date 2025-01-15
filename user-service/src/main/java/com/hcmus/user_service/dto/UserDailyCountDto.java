package com.hcmus.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDailyCountDto {
    private int totalUser;
    private String date;
}