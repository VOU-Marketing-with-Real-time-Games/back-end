package com.hcmus.gameservice.game_info.controller;

import com.hcmus.gameservice.game_info.dto.UserCampaignGameRequestDto;
import com.hcmus.gameservice.game_info.dto.UserCampaignGameResponseDto;
import com.hcmus.gameservice.game_info.exception.GameCampaignNotFoundException;
import com.hcmus.gameservice.game_info.exception.UserCampaignGameNotFoundException;
import com.hcmus.gameservice.game_info.service.UserCampaignGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user campaign games.
 */
@RestController
@RequestMapping("/v1/game/user-campaign-games")
@RequiredArgsConstructor
@Validated
public class UserCampaignGameApiController {

    private final UserCampaignGameService userCampaignGameService;

    /**
     * Creates a new user campaign game.
     *
     * @param requestDto the UserCampaignGameRequestDto containing the details of the user campaign game
     * @return the created UserCampaignGameResponseDto
     * @throws GameCampaignNotFoundException if the game campaign is not found
     */
    @PostMapping
    public ResponseEntity<UserCampaignGameResponseDto> createUserCampaignGame(@RequestBody UserCampaignGameRequestDto requestDto) throws GameCampaignNotFoundException {
        UserCampaignGameResponseDto responseDto = userCampaignGameService.saveUserCampaignGame(requestDto);
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
        UserCampaignGameResponseDto responseDto = userCampaignGameService.getUserCampaignGameById(id);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieves all user campaign games.
     *
     * @return a list of UserCampaignGameResponseDto
     */
    @GetMapping
    public ResponseEntity<List<UserCampaignGameResponseDto>> getAllUserCampaignGames() {
        List<UserCampaignGameResponseDto> responseDtos = userCampaignGameService.getAllUserCampaignGames();
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
        UserCampaignGameResponseDto responseDto = userCampaignGameService.updateUserCampaignGame(id);
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
        List<UserCampaignGameResponseDto> responseDtos = userCampaignGameService.findByUserId(userId);
        return ResponseEntity.ok(responseDtos);
    }

//    @GetMapping("/campaign/{campaignId}/users")
//    public ResponseEntity<List<Long>> findDistinctUserByCampaign(@PathVariable Long campaignId) throws CampaignNotFoundException {
//        List<Long> responseDtos = userCampaignGameService.getDistinctUserIdByCampaignId(campaignId);
//        return ResponseEntity.ok(responseDtos);
//    }

    @GetMapping("/campaign/users")
    public ResponseEntity<List<Long>> findDistinctUserByAllCampaign()  {
        List<Long> responseDtos = userCampaignGameService.getDistinctUserIdAllCampaigns();
        return ResponseEntity.ok(responseDtos);
    }
}