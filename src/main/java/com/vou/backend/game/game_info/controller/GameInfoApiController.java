package com.vou.backend.game.game_info.controller;

import com.vou.backend.game.game_info.dto.GameInfoDto;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing game information.
 */
@RestController
@RequestMapping("/game-info")
@RequiredArgsConstructor
@Validated
public class GameInfoApiController {

    private final GameService gameService;
    private final ModelMapper modelMapper;

    /**
     * Retrieves all game information.
     *
     * @return a list of GameInfoDto
     */
    @GetMapping
    public ResponseEntity<List<GameInfoDto>> getAllGames() {
        List<GameInfo> gameInfos = gameService.findAll();
        List<GameInfoDto> gameInfoDtos = gameInfos.stream()
                .map(gameInfo -> modelMapper.map(gameInfo, GameInfoDto.class))
                .collect(Collectors.toList());
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
        GameInfo gameInfo = gameService.findById(id);
        GameInfoDto gameInfoDto = modelMapper.map(gameInfo, GameInfoDto.class);
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
        GameInfo gameInfo = modelMapper.map(gameInfoDto, GameInfo.class);
        GameInfo updatedGameInfo = gameService.updateGame(id, gameInfo);
        GameInfoDto updatedGameInfoDto = modelMapper.map(updatedGameInfo, GameInfoDto.class);
        return ResponseEntity.ok(updatedGameInfoDto);
    }
}