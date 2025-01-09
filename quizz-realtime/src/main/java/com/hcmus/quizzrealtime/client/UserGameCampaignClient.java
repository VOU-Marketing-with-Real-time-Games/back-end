package com.hcmus.quizzrealtime.client;

import com.hcmus.quizzrealtime.dto.UserCampaignGameClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-game-campaign", url = "http://localhost:8001/v3/user-campaign-games")
public interface UserGameCampaignClient {
    @PostMapping("/add-by-user-ids-and-quizz-id")
    ResponseEntity<Void> addUserCampaignGamesByUserIdsAndQuizzId(
            @RequestBody UserCampaignGameClientRequest request);

    @PutMapping("/update-completed-by-user-ids-and-quizz-id")
    ResponseEntity<Void> updateListUserCampaignGame(
            @RequestBody UserCampaignGameClientRequest request);
}
