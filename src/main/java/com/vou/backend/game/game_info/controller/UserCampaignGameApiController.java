package com.vou.backend.game.game_info.controller;

import com.vou.backend.game.game_info.dto.UserCampaignGameRequestDto;
import com.vou.backend.game.game_info.dto.UserCampaignGameResponseDto;
import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.UserCampaignGameNotFoundException;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.game_info.service.UserCampaignGameService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing user campaign games.
 */
@RestController
@RequestMapping("/user-campaign-games")
@RequiredArgsConstructor
@Validated
public class UserCampaignGameApiController {

    private final UserCampaignGameService userCampaignGameService;
    private final ModelMapper modelMapper;

    /**
     * Creates a new user campaign game.
     *
     * @param requestDto the UserCampaignGameRequestDto containing the details of the user campaign game
     * @return the created UserCampaignGameResponseDto
     * @throws GameCampaignNotFoundException if the game campaign is not found
     */
    @PostMapping
    public ResponseEntity<UserCampaignGameResponseDto> createUserCampaignGame(@RequestBody UserCampaignGameRequestDto requestDto) throws GameCampaignNotFoundException {
        UserCampaignGame userCampaignGame = modelMapper.map(requestDto, UserCampaignGame.class);
        UserCampaignGame savedUserCampaignGame = userCampaignGameService.saveUserCampaignGame(userCampaignGame);
        UserCampaignGameResponseDto responseDto = modelMapper.map(savedUserCampaignGame, UserCampaignGameResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieves a user campaign game by its ID.
     *
     * @param id the ID of the user campaign game
     * @return the UserCampaignGameResponseDto
     * @throws UserCampaignGameNotFoundException if the user campaign game is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserCampaignGameResponseDto> getUserCampaignGameById(@PathVariable Long id) throws UserCampaignGameNotFoundException {
        UserCampaignGame userCampaignGame = userCampaignGameService.getUserCampaignGameById(id);
        UserCampaignGameResponseDto responseDto = modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieves all user campaign games.
     *
     * @return a list of UserCampaignGameResponseDto
     */
    @GetMapping
    public ResponseEntity<List<UserCampaignGameResponseDto>> getAllUserCampaignGames() {
        List<UserCampaignGame> userCampaignGames = userCampaignGameService.getAllUserCampaignGames();
        List<UserCampaignGameResponseDto> responseDtos = userCampaignGames.stream()
                .map(userCampaignGame -> modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * Updates a user campaign game to mark it as completed.
     *
     * @param id the ID of the user campaign game to update
     * @return the updated UserCampaignGameResponseDto
     * @throws UserCampaignGameNotFoundException if the user campaign game is not found
     * @throws GameCampaignNotFoundException if the game campaign is not found
     */
    @GetMapping("/{id}/completed")
    public ResponseEntity<UserCampaignGameResponseDto> updateUserCampaignGame(@PathVariable Long id) throws UserCampaignGameNotFoundException, GameCampaignNotFoundException {
        UserCampaignGame updatedUserCampaignGame = userCampaignGameService.updateUserCampaignGame(id);
        UserCampaignGameResponseDto responseDto = modelMapper.map(updatedUserCampaignGame, UserCampaignGameResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieves user campaign games by user ID.
     *
     * @param userId the ID of the user
     * @return a list of UserCampaignGameResponseDto
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCampaignGameResponseDto>> findByUserId(@PathVariable Long userId) {
        List<UserCampaignGame> userCampaignGames = userCampaignGameService.findByUserId(userId);
        List<UserCampaignGameResponseDto> responseDtos = userCampaignGames.stream()
                .map(userCampaignGame -> modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
}