package com.vou.backend.game.puzzle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PuzzleRequestDto {

    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    private String name;
    private String description;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer itemNum;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long campaignGameId;

    private List<ItemRequestDto> items;
}
