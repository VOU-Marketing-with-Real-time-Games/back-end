package com.hcmus.campaignservice.controller;

import com.hcmus.campaignservice.dto.*;
import com.hcmus.campaignservice.exception.CampaignAlreadyAddedException;
import com.hcmus.campaignservice.exception.CampaignNotFoundException;
import com.hcmus.campaignservice.model.FavoriteCampaignUser;
import com.hcmus.campaignservice.service.CampaignService;
import com.hcmus.campaignservice.service.FavoriteCampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;
    private final FavoriteCampaignService favoriteCampaignService;

    /**
     * Create a new campaign.
     *
     * @param campaignDTO the CampaignDto containing the details of the campaign
     * @return the created CampaignResponseDto
     */
    @PostMapping
    public ResponseEntity<?> createCampaign(@Valid @RequestBody CampaignDto campaignDTO) {
        CampaignResponseDto campaign = campaignService.createCampaign(campaignDTO);
        return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }

    /**
     * Get all campaigns.
     *
     * @param pageable the pageable information
     * @return a list of CampaignResponseDto
     */
    @GetMapping
    public ResponseEntity<?> getAllCampaigns(Pageable pageable) {
        Page<CampaignResponseDto> campaignList = campaignService.getAllCampaigns(pageable);
        return new ResponseEntity<>(campaignList, HttpStatus.OK);
    }

    /**
     * Get a campaign by its ID.
     *
     * @param id the ID of the campaign
     * @return the CampaignResponseDto
     * @throws CampaignNotFoundException if the campaign is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getACampaign(@PathVariable Long id) throws CampaignNotFoundException {
        CampaignResponseDto campaign = campaignService.getCampaign(id);
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }


    @GetMapping("/internal/{id}")
    public CampaignResponseDto getCampaign(@PathVariable Long id) throws CampaignNotFoundException {
        return campaignService.getCampaign(id);
    }

    /**
     * Update a campaign.
     *
     * @param id          the ID of the campaign
     * @param campaignDto the CampaignDto containing the details of the campaign
     * @return the updated CampaignResponseDto
     * @throws CampaignNotFoundException if the campaign is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateACampaign(@PathVariable Long id, @Valid @RequestBody UpdateCampaignDto campaignDto) throws CampaignNotFoundException {
        CampaignResponseDto campaign = campaignService.updateCampaign(id, campaignDto);
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }

    /**
     * Delete a campaign by its ID.
     *
     * @param id the ID of the campaign
     * @throws CampaignNotFoundException if the campaign is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable Long id) throws CampaignNotFoundException {
        campaignService.deleteCampaignById(id);
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * Get all campaigns by user ID.
     *
     * @param id the ID of the user
     * @return a list of CampaignResponseDto
     */
    @GetMapping("/user-favourite/{id}")
    public ResponseEntity<?> getFavoriteCampaign(@PathVariable Long id) {
        List<CampaignResponseDto> campaigns = campaignService.getFavouriteCampaignsByUser(id);
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    /**
     * Search campaigns by name.
     *
     * @param name the name of the campaign
     * @return a list of CampaignResponseDto
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchCampaignsByName(@RequestParam String name) {
        List<CampaignResponseDto> campaigns = campaignService.searchCampaignsByName(name);
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    /**
     * Get the newest campaign.
     *
     * @return the newest CampaignResponseDto
     */
    @GetMapping("/latest")
    public ResponseEntity<?> getNewestCampaign() {
        CampaignResponseDto newestCampaign = campaignService.getLatestCampaign();
        return new ResponseEntity<>(newestCampaign, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<CampaignStatisticsDto> getCampaignStatistics() {
        CampaignStatisticsDto stats = campaignService.getCampaignStatistics();
        return ResponseEntity.ok(stats);
    }


    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<CampaignResponseDto>> getCampaignsByBrandId(@PathVariable Long brandId) {
        List<CampaignResponseDto> campaigns = campaignService.getCampaignsByBrandId(brandId);
        return ResponseEntity.ok(campaigns);
    }
    /**
     * Add a campaign to the user's favorite list.
     *
     * @param addFavoriteDto the AddFavoriteDto containing the user ID and campaign ID
     * @return the FavoriteCampaignUser
     * @throws CampaignNotFoundException if the campaign is not found
     */
    @PostMapping("/add-favourite")
    public ResponseEntity<?> addFavoriteCampaign(@Valid @RequestBody AddFavoriteDto addFavoriteDto) throws CampaignNotFoundException, CampaignAlreadyAddedException, CampaignAlreadyAddedException {
        FavoriteCampaignUser favoriteCampaignUser = favoriteCampaignService.addFavoriteCampaign(addFavoriteDto.getUserId(), addFavoriteDto.getCampaignId());
        return new ResponseEntity<>(favoriteCampaignUser, HttpStatus.CREATED);
    }

    /**
     * Check if a campaign is in the user's favorite list.
     *
     * @param userId     the ID of the user
     * @param campaignId the ID of the campaign
     * @return true if the campaign is in the user's favorite list, false otherwise
     */
    @GetMapping("/user-favourite/{userId}/campaign/{campaignId}")
    public ResponseEntity<?> checkFavoriteCampaign(@PathVariable Long userId, @PathVariable Long campaignId) {
        boolean isFavorite = favoriteCampaignService.checkFavoriteCampaign(userId, campaignId);
        return new ResponseEntity<>(isFavorite, HttpStatus.OK);
    }
}
