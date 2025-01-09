package com.hcmus.campaignservice.repository;

import com.hcmus.campaignservice.model.FavoriteCampaignUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteCampaignUserRepository extends JpaRepository<FavoriteCampaignUser,Long> {
    List<FavoriteCampaignUser> findByUserId(Long userId);
}
