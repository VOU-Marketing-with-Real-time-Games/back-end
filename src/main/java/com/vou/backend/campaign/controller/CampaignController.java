package com.vou.backend.campaign.controller;

import com.vou.backend.campaign.dto.CampaignDto;
import com.vou.backend.campaign.dto.CampaignResponseDto;
import com.vou.backend.campaign.exception.CampaignNotFoundException;
import com.vou.backend.campaign.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;
    @PostMapping
    public ResponseEntity<?> createCampaign(@Valid @RequestBody CampaignDto campaignDTO) {
           CampaignResponseDto campaign = campaignService.createCampaign(campaignDTO);
           return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllCampaigns(Pageable pageable) {
        Page<CampaignResponseDto> campaignList = campaignService.getAllCampaigns(pageable);
        return new ResponseEntity<>(campaignList,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getACampaign(@PathVariable Long id) throws CampaignNotFoundException {
        CampaignResponseDto campaign = campaignService.getCampaign(id);
        return new ResponseEntity<>(campaign,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>  updateACampaign(@PathVariable Long id,@Valid @RequestBody CampaignDto campaignDto) throws CampaignNotFoundException {
            CampaignResponseDto campaign = campaignService.updateCampaign(id,campaignDto);
            return new ResponseEntity<>(campaign,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable Long id) throws CampaignNotFoundException {
        campaignService.deleteCampaignById(id);
        return ResponseEntity.noContent().build();
    }
}
