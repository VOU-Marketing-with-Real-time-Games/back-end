package com.vou.backend.game.puzzle.dto;

import com.vou.backend.game.puzzle.model.Item;
import jakarta.persistence.*;
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
