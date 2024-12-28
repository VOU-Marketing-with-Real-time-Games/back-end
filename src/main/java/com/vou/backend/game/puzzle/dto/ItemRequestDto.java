package com.vou.backend.game.puzzle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequestDto {
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer position;

    private String description;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer total;
}
