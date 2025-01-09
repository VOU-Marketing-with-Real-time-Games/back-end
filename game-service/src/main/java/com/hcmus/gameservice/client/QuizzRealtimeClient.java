package com.hcmus.gameservice.client;

import com.hcmus.gameservice.quizz.dto.ScheduleRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "quizz-realtime", url = "http://localhost:8006/v3/quizz-realtime")
public interface QuizzRealtimeClient {
    @PostMapping("/schedule")
    boolean scheduleQuiz(@RequestBody ScheduleRequestDto request);
}
