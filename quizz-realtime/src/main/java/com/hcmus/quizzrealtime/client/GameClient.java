package com.hcmus.quizzrealtime.client;

import com.hcmus.quizzrealtime.dto.UserQuizzTotalScoreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "game-service", url = "http://localhost:8001/v3/user-answers")
public interface GameClient {
    @GetMapping("/total-score/{quizzId}")
    List<UserQuizzTotalScoreDto> getTotalScoreUserInQuizz(@PathVariable Long quizzId);
}
