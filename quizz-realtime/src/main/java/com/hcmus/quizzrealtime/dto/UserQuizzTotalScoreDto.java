package com.hcmus.quizzrealtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizzTotalScoreDto {
    private Long userId;
    private String fullName;
    private String imageUrl;
    private int totalScore;
    private Long quizzId;
}
