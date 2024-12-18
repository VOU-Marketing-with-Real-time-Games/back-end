package com.vou.backend.game.game_info.controller;

import com.vou.backend.game.game_info.dto.GameCampaignRequestDto;
import com.vou.backend.game.game_info.dto.GameCampaignResponseDto;
import com.vou.backend.game.game_info.dto.UserCampaignGameResponseDto;
import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.game_info.service.GameCampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing game campaigns.
 */
@RestController
@RequestMapping("/game-campaigns")
@RequiredArgsConstructor
@Validated
public class GameCampaignApiController {
    private final GameCampaignService gameCampaignService;

    /**
     * Retrieves all game campaigns.
     *
     * @return a list of GameCampaignResponseDto
     */
    @GetMapping
    public ResponseEntity<List<GameCampaignResponseDto>> getAllGameCampaigns() {

        List<GameCampaignResponseDto> responseDtos = gameCampaignService.findAll();
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * Retrieves a game campaign by its ID.
     *
     * @param id the ID of the game campaign
     * @return the GameCampaignResponseDto
     * @throws GameCampaignNotFoundException if the game campaign is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<GameCampaignResponseDto> getGameCampaignById(@PathVariable Long id) throws GameCampaignNotFoundException {
        GameCampaignResponseDto responseDto = gameCampaignService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Creates a new game campaign.
     *
     * @param requestDto the GameCampaignRequestDto containing the details of the game campaign
     * @return the created GameCampaignResponseDto
     * @throws GameNotFoundException if the game is not found
     */
    @PostMapping
    public ResponseEntity<GameCampaignResponseDto> createGameCampaign(@RequestBody @Valid GameCampaignRequestDto requestDto) throws GameNotFoundException {
        GameCampaignResponseDto responseDto = gameCampaignService.createGameCampaign(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Updates an existing game campaign.
     *
     * @param id the ID of the game campaign to update
     * @param requestDto the GameCampaignRequestDto containing the updated details
     * @return the updated GameCampaignResponseDto
     * @throws GameCampaignNotFoundException if the game campaign is not found
     * @throws GameNotFoundException if the game is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<GameCampaignResponseDto> updateGameCampaign(@PathVariable Long id, @RequestBody @Valid GameCampaignRequestDto requestDto) throws GameCampaignNotFoundException, GameNotFoundException {
        GameCampaignResponseDto responseDto = gameCampaignService.updateGameCampaign(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Deletes a game campaign by its ID.
     *
     * @param id the ID of the game campaign to delete
     * @return a ResponseEntity with no content
     * @throws GameCampaignNotFoundException if the game campaign is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGameCampaign(@PathVariable Long id) throws GameCampaignNotFoundException {
        gameCampaignService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves game campaigns by campaign ID.
     *
     * @param campaignId the ID of the campaign
     * @return a list of GameCampaignResponseDto
     */
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<GameCampaignResponseDto>> findByCampaignId(@PathVariable Long campaignId) {
        List<GameCampaignResponseDto> gameCampaignDtos = gameCampaignService.findByCampaignId(campaignId);
        return ResponseEntity.ok(gameCampaignDtos);
    }

    /**
     * Retrieves user campaign games by campaign ID.
     *
     * @param campaignId the ID of the campaign
     * @return a list of UserCampaignGameResponseDto
     * @throws GameCampaignNotFoundException if the game campaign is not found
     */
    @GetMapping("/{campaignId}/users")
    public ResponseEntity<List<UserCampaignGameResponseDto>> getUserCampaignGames(@PathVariable Long campaignId) throws GameCampaignNotFoundException {
        List<UserCampaignGameResponseDto> responseDtos = gameCampaignService.getUserCampaignGames(campaignId);
        return ResponseEntity.ok(responseDtos);
    }
}
