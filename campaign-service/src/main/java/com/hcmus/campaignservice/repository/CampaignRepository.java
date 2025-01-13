package com.hcmus.campaignservice.repository;

import com.hcmus.campaignservice.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
    @Query("SELECT c FROM Campaign c WHERE c.name LIKE %:name%")
    List<Campaign> findByNameContaining(@Param("name") String name);
}
