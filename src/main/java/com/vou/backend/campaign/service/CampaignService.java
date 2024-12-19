package com.vou.backend.campaign.service;

import com.vou.backend.campaign.dto.CampaignDto;
import com.vou.backend.campaign.dto.CampaignResponseDto;
import com.vou.backend.campaign.exception.CampaignNotFoundException;
import com.vou.backend.campaign.model.Campaign;
import com.vou.backend.campaign.model.FavoriteCampaignUser;
import com.vou.backend.campaign.repository.CampaignRepository;
import com.vou.backend.campaign.repository.FavoriteCampaignUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {
    private  final CampaignRepository campaignRepository;
    private final FavoriteCampaignUserRepository favoriteCampaignUserRepository;
    private final ModelMapper modelMapper;
    public CampaignResponseDto createCampaign(CampaignDto campaignDto)
    {
        Date current = new Date();
        Campaign campaign = modelMapper.map(campaignDto, Campaign.class);
        campaign.setCreatedAt(current);
        campaign.setStatus("Pending");
        return modelMapper.map(campaignRepository.save(campaign),CampaignResponseDto.class);
    }
    public Page<CampaignResponseDto> getAllCampaigns(Pageable pageable) {
        return campaignRepository.findAll(pageable).map(value -> modelMapper.map(value,CampaignResponseDto.class));
    }
    public CampaignResponseDto getCampaign(Long id) throws CampaignNotFoundException
    {
        validateId(id);
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found with id: " + id));
        return modelMapper.map(campaign,CampaignResponseDto.class);
    }
    public CampaignResponseDto updateCampaign(Long id, CampaignDto campaignDto) throws CampaignNotFoundException
    {
        validateId(id);
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found with id: " + id));
        modelMapper.map(campaignDto, campaign);
        return modelMapper.map(campaignRepository.save(campaign),CampaignResponseDto.class);
    }
    public void deleteCampaignById(Long id) throws CampaignNotFoundException {
        validateId(id);
        getCampaign(id);
        campaignRepository.deleteById(id);
    }
    public List<CampaignResponseDto> getFavouriteCampaignsByUser(Long userId) {
        validateId(userId);
        List<FavoriteCampaignUser> favouriteCampaigns = favoriteCampaignUserRepository.findByUserId(userId);
        return favouriteCampaigns.stream()
                .map(favoriteCampaignUser -> modelMapper.map(favoriteCampaignUser.getCampaign(), CampaignResponseDto.class))
                .collect(Collectors.toList());
    }
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
}
