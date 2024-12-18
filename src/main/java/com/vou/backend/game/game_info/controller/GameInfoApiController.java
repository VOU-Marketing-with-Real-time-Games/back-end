package com.vou.backend.game.game_info.controller;

import com.vou.backend.game.game_info.dto.GameInfoDto;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing game information.
 */
@RestController
@RequestMapping("/game-info")
@RequiredArgsConstructor
@Validated
public class GameInfoApiController {

    private final GameService gameService;
    /**
     * Retrieves all game information.
     *
     * @return a list of GameInfoDto
     */
    @GetMapping
    public ResponseEntity<List<GameInfoDto>> getAllGames() {
        List<GameInfoDto> gameInfoDtos = gameService.findAll();
        return ResponseEntity.ok(gameInfoDtos);
    }

    /**
     * Retrieves game information by its ID.
     *
     * @param id the ID of the game
     * @return the GameInfoDto
     * @throws GameNotFoundException if the game is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<GameInfoDto> getGameById(@PathVariable Long id) throws GameNotFoundException {
        GameInfoDto gameInfoDto = gameService.findById(id);
        return ResponseEntity.ok(gameInfoDto);
    }

    /**
     * Updates an existing game information.
     *
     * @param id the ID of the game to update
     * @param gameInfoDto the GameInfoDto containing the updated details
     * @return the updated GameInfoDto
     * @throws GameNotFoundException if the game is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<GameInfoDto> updateGame(@PathVariable Long id, @Valid @RequestBody GameInfoDto gameInfoDto) throws GameNotFoundException {
        GameInfoDto updatedGameInfoDto = gameService.updateGame(id, gameInfoDto);
        return ResponseEntity.ok(updatedGameInfoDto);
    }
}