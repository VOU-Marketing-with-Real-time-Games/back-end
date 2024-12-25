package com.vou.backend.campaign.repository;
import com.vou.backend.campaign.model.FavoriteCampaignUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteCampaignUserRepository extends JpaRepository<FavoriteCampaignUser,Long> {
    List<FavoriteCampaignUser> findByUserId(Long userId);
}
