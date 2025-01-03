package com.hcmus.gameservice.puzzle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserItemDto {
    private Long id;

    private Long userId;

    private ItemResponseDto item;

    private Integer totalItem;
}
