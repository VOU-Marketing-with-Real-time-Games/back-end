package com.vou.backend.campaign.service;

import com.vou.backend.campaign.exception.CampaignAlreadyAddedException;
import com.vou.backend.campaign.exception.CampaignNotFoundException;
import com.vou.backend.campaign.model.Campaign;
import com.vou.backend.campaign.model.FavoriteCampaignUser;
import com.vou.backend.campaign.repository.CampaignRepository;
import com.vou.backend.campaign.repository.FavoriteCampaignUserRepository;
import com.vou.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FavoriteCampaignService {
    private final FavoriteCampaignUserRepository favoriteCampaignUserRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    public FavoriteCampaignUser addFavoriteCampaign(Long userId, Long campaignId) throws CampaignNotFoundException, CampaignAlreadyAddedException {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found"));
        userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(favoriteCampaignUserRepository.findByUserIdAndCampaignId(userId,campaignId).isPresent()){
            throw new CampaignAlreadyAddedException("Campaign already added to favorites");
        }
        FavoriteCampaignUser favorite = new FavoriteCampaignUser();
        favorite.setUserId(userId);
        favorite.setCampaign(campaign);
        favorite.setAddToTime(new Date());
        return favoriteCampaignUserRepository.save(favorite);
    }
}
