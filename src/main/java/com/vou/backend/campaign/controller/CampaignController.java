package com.vou.backend.campaign.controller;
import com.vou.backend.campaign.dto.CampaignDto;
import com.vou.backend.campaign.dto.CampaignResponseDto;
import com.vou.backend.campaign.dto.UpdateCampaignDto;
import com.vou.backend.campaign.exception.CampaignNotFoundException;
import com.vou.backend.campaign.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;
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
        return new ResponseEntity<>(campaignList,HttpStatus.OK);
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
        return new ResponseEntity<>(campaign,HttpStatus.OK);
    }
    /**
     * Update a campaign.
     *
     * @param id the ID of the campaign
     * @param campaignDto the CampaignDto containing the details of the campaign
     * @return the updated CampaignResponseDto
     * @throws CampaignNotFoundException if the campaign is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?>  updateACampaign(@PathVariable Long id,@Valid @RequestBody UpdateCampaignDto campaignDto) throws CampaignNotFoundException {
            CampaignResponseDto campaign = campaignService.updateCampaign(id,campaignDto);
            return new ResponseEntity<>(campaign,HttpStatus.OK);
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
        return ResponseEntity.noContent().build();
    }
    /**
     * Get all campaigns by user ID.
     *
     * @param id the ID of the user
     * @return a list of CampaignResponseDto
     */
    @GetMapping("/user-favourite/{id}")
    public ResponseEntity<?> getFavoriteCampaign(@PathVariable Long id)
    {
        List<CampaignResponseDto> campaigns = campaignService.getFavouriteCampaignsByUser(id);
        return new ResponseEntity<>(campaigns,HttpStatus.OK);
    }
}
