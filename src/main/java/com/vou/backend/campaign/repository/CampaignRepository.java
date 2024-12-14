package com.vou.backend.campaign.repository;
import com.vou.backend.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
}
