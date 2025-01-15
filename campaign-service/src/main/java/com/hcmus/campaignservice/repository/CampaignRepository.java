package com.hcmus.campaignservice.repository;

import com.hcmus.campaignservice.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
    @Query("SELECT c FROM Campaign c WHERE c.name LIKE %:name%")
    List<Campaign> findByNameContaining(@Param("name") String name);
    @Query("SELECT COUNT(c) FROM Campaign c WHERE DATE_FORMAT(c.createdAt, '%Y-%m-%d') = :date")
int countCampaignsByDate(@Param("date") String date);
}
