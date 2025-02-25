package com.hcmus.campaignservice.service;

import com.hcmus.campaignservice.dto.*;
import com.hcmus.campaignservice.exception.CampaignNotFoundException;
import com.hcmus.campaignservice.model.Campaign;
import com.hcmus.campaignservice.model.FavoriteCampaignUser;
import com.hcmus.campaignservice.repository.CampaignRepository;
import com.hcmus.campaignservice.repository.FavoriteCampaignUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class

CampaignService {
    private final CampaignRepository campaignRepository;
    private final FavoriteCampaignUserRepository favoriteCampaignUserRepository;
    private final ModelMapper modelMapper;
    /**
     * Create a new campaign
     * @param campaignDto
     * @return
     */
    public CampaignResponseDto createCampaign(CampaignDto campaignDto)
    {
        Date current = new Date();
        Campaign campaign = modelMapper.map(campaignDto, Campaign.class);
        campaign.setCreatedAt(current);
        campaign.setStatus("PENDING");
        return modelMapper.map(campaignRepository.save(campaign),CampaignResponseDto.class);
    }
    /**
     * Get all campaigns
     * @param pageable
     * @return
     */
    public Page<CampaignResponseDto> getAllCampaigns(Pageable pageable) {
        return campaignRepository.findAll(pageable).map(value -> modelMapper.map(value,CampaignResponseDto.class));
    }
    /**
     * Get a campaign by id
     * @param id
     * @return
     * @throws CampaignNotFoundException
     */
    public CampaignResponseDto getCampaign(Long id) throws CampaignNotFoundException
    {
        validateId(id);
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found with id: " + id));
        return modelMapper.map(campaign,CampaignResponseDto.class);
    }

    /**
     * Find a campaign by id
     * @param id
     * @return
     * @throws CampaignNotFoundException
     */
    public Campaign findCampaign(Long id) throws CampaignNotFoundException
    {
        validateId(id);
        return campaignRepository.findById(id)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found with id: " + id));
    }
    /**
     * Search campaigns by name
     * @param name
     * @return
     */
    public List<CampaignResponseDto> searchCampaignsByName(String name) {
        List<Campaign> campaigns = campaignRepository.findByNameContaining(name);
        return campaigns.stream()
                .map(campaign -> modelMapper.map(campaign, CampaignResponseDto.class))
                .collect(Collectors.toList());
    }
    /**
     * Update a campaign
     * @param id
     * @param campaignDto
     * @return
     * @throws CampaignNotFoundException
     */
    public CampaignResponseDto updateCampaign(Long id, UpdateCampaignDto campaignDto) throws CampaignNotFoundException
    {
        validateId(id);
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found with id: " + id));
        modelMapper.map(campaignDto, campaign);
        return modelMapper.map(campaignRepository.save(campaign),CampaignResponseDto.class);
    }
    /**
     * Delete a campaign by id
     * @param id
     * @throws CampaignNotFoundException
     */
    public void deleteCampaignById(Long id) throws CampaignNotFoundException {
        validateId(id);
        getCampaign(id);
        campaignRepository.deleteById(id);
    }

    /**
     * Get favourite campaigns by user
     * @param userId
     * @return
     */
    public List<CampaignResponseDto> getFavouriteCampaignsByUser(Long userId) {
        validateId(userId);
        List<FavoriteCampaignUser> favouriteCampaigns = favoriteCampaignUserRepository.findByUserId(userId);
        return favouriteCampaigns.stream()
                .map(favoriteCampaignUser -> modelMapper.map(favoriteCampaignUser.getCampaign(), CampaignResponseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Validate ID
     * @param id
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
    /**
     * Get latest campaign
     * @return
     */
    public CampaignResponseDto getLatestCampaign() {
        Campaign newestCampaign = campaignRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().findFirst().orElse(null);
        return modelMapper.map(newestCampaign, CampaignResponseDto.class);
    }
    /**
     * Get campaign statistics
     * @return
     */
    public CampaignStatisticsDto getCampaignStatistics() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Integer> dailyCampaignCounts = new ArrayList<>();
        int totalCampaigns = 0;
        int previousDayCount = 0;
        boolean isTrendUp = false;

        for (int i = 0; i < 30; i++) {
            LocalDate date = startDate.plusDays(i);
            String formattedDate = date.format(formatter);
            int campaignCount = campaignRepository.countCampaignsByDate(formattedDate);
            dailyCampaignCounts.add(campaignCount);
            totalCampaigns += campaignCount;

            if (i > 0 && campaignCount > previousDayCount) {
                isTrendUp = true;
            }
            previousDayCount = campaignCount;
        }

        CampaignStatisticsDto stats = new CampaignStatisticsDto();
        stats.setTitle("Campaigns");
        stats.setValue(String.valueOf(totalCampaigns));
        stats.setInterval("Last 30 days");
        stats.setTrend(isTrendUp ? "up" : "down");
        stats.setData(dailyCampaignCounts);

        return stats;
    }

    /**
     * Get campaigns by brand ID
     * @param brandId
     * @return
     */
    public List<CampaignResponseDto> getCampaignsByBrandId(Long brandId) {
        List<Campaign> campaigns = campaignRepository.findByBrandId(brandId);
        return campaigns.stream()
                .map(campaign -> modelMapper.map(campaign, CampaignResponseDto.class))
                .collect(Collectors.toList());
    }
    /**
     * Get campaign daily counts
     * @return
     */
    public List<CampaignDailyCountDto> getCampaignDailyCounts() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<CampaignDailyCountDto> dailyCounts = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            LocalDate date = startDate.plusDays(i);
            String formattedDate = date.format(formatter);
            int campaignCount = campaignRepository.countCampaignsByDate(formattedDate);
            dailyCounts.add(new CampaignDailyCountDto(campaignCount, formattedDate));
        }

        return dailyCounts;
    }
}
