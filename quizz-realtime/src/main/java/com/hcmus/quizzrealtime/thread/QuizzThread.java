package com.hcmus.quizzrealtime.thread;

import com.hcmus.quizzrealtime.client.GameClient;
import com.hcmus.quizzrealtime.client.UserClient;
import com.hcmus.quizzrealtime.client.UserGameCampaignClient;
import com.hcmus.quizzrealtime.client.VoucherClient;
import com.hcmus.quizzrealtime.dto.*;
import com.hcmus.quizzrealtime.rabbit_mq.NotificationDto;
import com.hcmus.quizzrealtime.rabbit_mq.NotificationService;
import com.hcmus.quizzrealtime.socket.QuizSocketHandler;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class QuizzThread implements Runnable {
    private QuizzDto quizz;
    private QuizSocketHandler quizSocketHandler;
    private List<QuestionDto> questionResponseDto;
    private GameClient gameClient;
    private UserGameCampaignClient userGameCampaignClient;
    private NotificationService notificationService;
    private VoucherClient voucherClient;
    private AtomicInteger count = new AtomicInteger(0);
    private UserClient userClient;

    public QuizzThread(QuizzDto quizz, QuizSocketHandler quizWebSocketHandler, List<QuestionDto> questionResponseDto, GameClient gameClient,
                       UserGameCampaignClient userGameCampaignClient, NotificationService notificationService, VoucherClient voucherClient, UserClient userClient) {
        this.quizz = quizz;
        this.quizSocketHandler = quizWebSocketHandler;
        this.questionResponseDto = questionResponseDto;
        this.gameClient = gameClient;
        this.userGameCampaignClient = userGameCampaignClient;
        this.notificationService = notificationService;
        this.voucherClient = voucherClient;
        this.userClient = userClient;
    }

    public synchronized void updateCount(List<Long> userIds) {
        if (count.incrementAndGet() == questionResponseDto.size()) {
            // Handle statistics, reward to user and end quizz
            voucherClient.takeVoucherAfterQuizz(new VoucherUserRequestDto(quizz.getId(), userIds));
            for(Long userId : userIds) {
                NotificationDto notificationDto = new NotificationDto();
                notificationDto.setUserId(userId);
                notificationDto.setContent("You have won the quizz! And revice a voucher");
                notificationService.notifyGameEvent(notificationDto);
            }
            // End quizz
            updateUserCampaignGame(quizSocketHandler.getUserIdsByQuizzId(quizz.getId()));
        }
    }

    @Override
    public void run()
    {
        quizSocketHandler.addUserCampaignGame(quizz.getId(), this);
        quizSocketHandler.startQuiz(quizz.getId(), questionResponseDto, this);
    }

    public void addUserCampaignGame(List<Long> userIds) {
        userClient.decreaseTurnNumForUsers(userIds);
        userGameCampaignClient.addUserCampaignGamesByUserIdsAndQuizzId(new UserCampaignGameClientRequest(userIds, quizz.getId()));
    }

    public  void updateUserCampaignGame(List<Long> userIds) {
        userGameCampaignClient.updateListUserCampaignGame(new UserCampaignGameClientRequest(userIds, quizz.getId()));
    }

    private List<Long> getTop3UserIds(List<UserQuizzTotalScoreDto> userQuizzTotalScoreDtos) {
        return userQuizzTotalScoreDtos.stream()
                .sorted(Comparator.comparingInt(UserQuizzTotalScoreDto::getTotalScore).reversed())
                .limit(3)
                .map(UserQuizzTotalScoreDto::getUserId)
                .collect(Collectors.toList());
    }

    public void totalScoreUserInQuiz() {
       List<UserQuizzTotalScoreDto> userQuizzTotalScoreDtos = gameClient.getTotalScoreUserInQuizz(quizz.getId());
       quizSocketHandler.sendResultsToUsers(userQuizzTotalScoreDtos, quizz.getId());
       updateCount(getTop3UserIds(userQuizzTotalScoreDtos));
    }
}
