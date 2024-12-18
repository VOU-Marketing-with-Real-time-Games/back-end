package com.vou.backend.game.puzzle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDto {
    private Long id;
    private Integer position;
    private String description;

    private Integer total;
    private Integer remainingNum;
}
