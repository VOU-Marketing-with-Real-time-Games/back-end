package com.hcmus.user_service.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hcmus.user_service.service.UserService;


@Component
public class DailyTaskScheduler {

    private final UserService userService;

    public DailyTaskScheduler(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 6 * * *") // Every day at 6 AM
    public void resetPlayerPlayTurn() {
        userService.resetPlayerPlayTurn();
    }
}

