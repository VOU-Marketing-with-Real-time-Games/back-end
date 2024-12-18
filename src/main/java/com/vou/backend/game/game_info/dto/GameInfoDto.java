package com.vou.backend.game.game_info.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for GameInfo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameInfoDto {

    /**
     * The ID of the game.
     */
    private Long id;

    /**
     * The name of the game.
     * Cannot be blank.
     */
    @NotBlank(message = "Name cannot be blank")
    private String name;

    /**
     * The image URL of the game.
     */
    private String image;

    /**
     * The type of the game.
     * Must be either QUIZZ or SHAKE_GAME.
     * Cannot be null.
     */
    @NotNull(message = "Game type cannot be null")
    @Pattern(regexp = "QUIZZ|SHAKE_GAME", message = "Game type must be either QUIZZ or SHAKE_GAME")
    private String type;

    /**
     * The manual or instructions for the game.
     * Cannot be blank or null.
     */
    @NotBlank(message = "Manual cannot be blank")
    @NotNull(message = "Manual cannot be null")
    private String manual;

    /**
     * Indicates whether the game is enabled.
     */
    private Boolean enable;
}