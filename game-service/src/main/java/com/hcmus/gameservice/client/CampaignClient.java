package com.hcmus.gameservice.client;

import com.hcmus.gameservice.game_info.dto.CampaignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "campaign-service", url = "http://localhost:8006/v3/quizz-realtime")
public interface CampaignClient {
    @GetMapping("/internal/{id}")
    CampaignDto getCampaign(Long id);
}
