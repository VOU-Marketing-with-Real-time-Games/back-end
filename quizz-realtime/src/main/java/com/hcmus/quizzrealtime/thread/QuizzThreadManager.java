package com.hcmus.quizzrealtime.thread;

import com.hcmus.quizzrealtime.client.GameClient;
import com.hcmus.quizzrealtime.client.UserGameCampaignClient;
import com.hcmus.quizzrealtime.client.VoucherClient;
import com.hcmus.quizzrealtime.dto.QuestionDto;
import com.hcmus.quizzrealtime.dto.QuizzDto;
import com.hcmus.quizzrealtime.rabbit_mq.NotificationService;
import com.hcmus.quizzrealtime.socket.QuizSocketHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class QuizzThreadManager {
    private final ExecutorService executorService;
    private final QuizSocketHandler quizWebSocketHandler;
    private final int threadPoolSize = 100;
    private final GameClient gameClient;
    private final UserGameCampaignClient userGameCampaignClient;
    private final NotificationService notificationService;
    private final VoucherClient voucherClient;

    public QuizzThreadManager(QuizSocketHandler quizSocketHandler, GameClient gameClient, UserGameCampaignClient userGameCampaignClient, NotificationService notificationService, VoucherClient voucherClient) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize); // Thread pool size
        this.quizWebSocketHandler = quizSocketHandler;
        this.gameClient = gameClient;
        this.userGameCampaignClient = userGameCampaignClient;
        this.notificationService = notificationService;
        this.voucherClient = voucherClient;
    }

    public void startGame(QuizzDto quizz, List<QuestionDto> questionResponseDtos) {
        executorService.submit(new QuizzThread(quizz, quizWebSocketHandler, questionResponseDtos, gameClient, userGameCampaignClient, notificationService, voucherClient));
    }
}
