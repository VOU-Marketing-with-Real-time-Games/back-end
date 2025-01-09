package com.hcmus.quizzrealtime.service;


import com.hcmus.quizzrealtime.dto.QuestionDto;
import com.hcmus.quizzrealtime.dto.QuizzDto;
import com.hcmus.quizzrealtime.thread.QuizzThreadManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class TaskSchedulerService {
    private final TaskScheduler taskScheduler; // Spring TaskScheduler
    private final QuizzThreadManager quizzThreadManager;
    private final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerService.class);

    // Map to store scheduled tasks by quiz ID
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public void scheduleQuizStart(QuizzDto quizz, List<QuestionDto> questionResponseDtos) {
        // Cancel and remove any existing task for the given quiz
        cancelScheduledTask(quizz.getId());
        // Log the current time and scheduled time
        LOGGER.info("Current time: {}", new Date());
        LOGGER.info("Scheduled start time: {}", quizz.getStartTime());

        // Schedule the new task
        ScheduledFuture<?> future = taskScheduler.schedule(() -> startGameThread(quizz, questionResponseDtos), Instant.parse(quizz.getStartTime()));
        // Store the task in the map for potential future cancellation
        scheduledTasks.put(quizz.getId(), future);
    }

    private void cancelScheduledTask(Long quizId) {
        ScheduledFuture<?> future = scheduledTasks.remove(quizId);
        if (future != null && !future.isDone()) {
            future.cancel(true); // Cancel the task
        }
    }

    private void startGameThread(QuizzDto quizz, List<QuestionDto> questionResponseDtos) {
        quizzThreadManager.startGame(quizz, questionResponseDtos);
    }
}
