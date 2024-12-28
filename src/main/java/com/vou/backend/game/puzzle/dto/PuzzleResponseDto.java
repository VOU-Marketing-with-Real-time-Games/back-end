package com.vou.backend.game.puzzle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PuzzleResponseDto {
    private Long id;
    private String image;
    private String name;
    private String description;
    private Integer itemNum;
    private Long campaignGameId;
    private List<ItemResponseDto> items;
}
