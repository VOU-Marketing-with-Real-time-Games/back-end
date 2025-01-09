package com.hcmus.campaignservice.repository;

import com.hcmus.campaignservice.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
}
