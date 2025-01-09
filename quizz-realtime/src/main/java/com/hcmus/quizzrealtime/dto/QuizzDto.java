package com.hcmus.quizzrealtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizzDto {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Integer secondPerQuestion;
    private Date startTime;
    private Long campaignGameId;
}
