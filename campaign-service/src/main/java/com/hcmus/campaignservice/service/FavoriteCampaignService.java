package com.hcmus.campaignservice.service;
import com.hcmus.campaignservice.client.UserClient;
import com.hcmus.campaignservice.dto.UserRespondDto;
import com.hcmus.campaignservice.exception.CampaignAlreadyAddedException;
import com.hcmus.campaignservice.exception.CampaignNotFoundException;
import com.hcmus.campaignservice.model.Campaign;
import com.hcmus.campaignservice.model.FavoriteCampaignUser;
import com.hcmus.campaignservice.repository.CampaignRepository;
import com.hcmus.campaignservice.repository.FavoriteCampaignUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FavoriteCampaignService {
    private final FavoriteCampaignUserRepository favoriteCampaignUserRepository;
    private final UserClient userClient;
    private final CampaignRepository campaignRepository;
    public FavoriteCampaignUser addFavoriteCampaign(Long userId, Long campaignId) throws CampaignNotFoundException, CampaignAlreadyAddedException {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found"));
        UserRespondDto userRespondDto = userClient.getUserByID(userId);
        if(favoriteCampaignUserRepository.findByUserIdAndCampaignId(userId,campaignId).isPresent()){
            throw new CampaignAlreadyAddedException("Campaign already added to favorites");
        }
        FavoriteCampaignUser favorite = new FavoriteCampaignUser();
        favorite.setUserId(userId);
        favorite.setCampaign(campaign);
        favorite.setAddToTime(new Date());
        return favoriteCampaignUserRepository.save(favorite);
    }
    public boolean checkFavoriteCampaign(Long userId, Long campaignId){
        return favoriteCampaignUserRepository.findByUserIdAndCampaignId(userId,campaignId).isPresent();
    }
}