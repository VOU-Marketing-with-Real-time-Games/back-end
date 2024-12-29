package com.vou.backend.quizz_realtime.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.backend.game.quizz.dto.QuestionResponseDto;
import com.vou.backend.game.quizz.dto.UserQuizzTotalScoreDto;
import com.vou.backend.game.quizz.model.Question;
import com.vou.backend.game.quizz.model.UserAnswer;
import com.vou.backend.game.quizz.service.UserAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    // Map to track connected users by quiz ID
    private final Map<Long, Map<Long, WebSocketSession>> quizUserSessions = new ConcurrentHashMap<>(); // quizzId -> {userId -> sessionId}

    private final Map<Long, List<Long>> quizAnswerCompletion = new ConcurrentHashMap<>();

    // Map to track quiz states
    private final Map<Long, String> quizStates = new ConcurrentHashMap<>(); // quizzId -> state ("WAITING", "HAPPENING")

    private final UserAnswerService userAnswerService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("New connection established: {}", session.getId());
    }

    private void handleConnectQuiz(WebSocketSession session, Long userId, Long quizzId) throws IOException {
        // Validate if quiz is still in the "WAITING" state
        String quizState = quizStates.getOrDefault(quizzId, "WAITING");
        if ("HAPPENING".equals(quizState)) {
            session.sendMessage(new TextMessage("Quiz already started, connection denied."));
            session.close();
            return;
        }
        // Map the user to the quiz
        quizUserSessions.computeIfAbsent(quizzId, k -> new ConcurrentHashMap<>()).put(userId, session);
        log.info("User {} connected to quiz {}", userId, quizzId);
    }

    private void handleAnswerComplete(Long userId, Long quizzId) {
        // Ensure thread-safe addition of userId to the quiz's completion list
        quizAnswerCompletion.compute(quizzId, (key, currentList) -> {
            if (currentList == null) {
                currentList = new CopyOnWriteArrayList<>();
            }
            if (!currentList.contains(userId)) {
                currentList.add(userId);
            }
            return currentList;
        });

        // Check if all connected users have completed the quiz
        Map<Long, WebSocketSession> connectedUsers = quizUserSessions.get(quizzId);
        List<Long> completedUsers = quizAnswerCompletion.get(quizzId);

        if (connectedUsers != null && completedUsers != null) {
            boolean allCompleted = connectedUsers.keySet().stream().allMatch(completedUsers::contains);

            if (allCompleted) {
                // Send results to all connected users
                sendResultsToUsers(quizzId, connectedUsers);

                // Reset the quizAnswerCompletion list for this quizzId
                quizAnswerCompletion.remove(quizzId);
            }
        }
    }

    private void sendResultsToUsers(Long quizzId, Map<Long, WebSocketSession> connectedUsers) {
        List<UserQuizzTotalScoreDto> userQuizzTotalScoreDto = userAnswerService.totalScoreUserInQuizz(quizzId);
        // Send results to all connected users
        for (Map.Entry<Long, WebSocketSession> entry : connectedUsers.entrySet()) {
            try {
                WebSocketSession session = entry.getValue();
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(userQuizzTotalScoreDto)));
                }
            } catch (IOException e) {
                log.error("Error sending results to user {}", entry.getKey(), e);
            }
        }

        log.info("Results sent for quiz {}", quizzId);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, String> clientMessage = objectMapper.readValue(payload, Map.class);
        String type = clientMessage.get("type"); // Extract the type parameter
        Long userId = Long.parseLong(clientMessage.get("userId"));
        Long quizzId = Long.parseLong(clientMessage.get("quizzId"));

        // Validate the type parameter
        if (type == null || type.isEmpty()) {
            session.sendMessage(new TextMessage("Invalid message type."));
            return;
        }

        switch (type) {
            case "CONNECT_QUIZ": {
                handleConnectQuiz(session,userId, quizzId);
                break;
            }
            case "ANSWER_COMPLETE": {
                handleAnswerComplete(userId, quizzId);
                break;
            }
            default:
                session.sendMessage(new TextMessage("Unknown message type."));
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connection closed: {}", session.getId());

        // Remove user from any quiz they were connected to
        quizUserSessions.forEach((quizId, userSessions) ->
                userSessions.entrySet().removeIf(entry -> entry.getValue().equals(session)));
    }

    public void startQuiz(Long quizzId, List<QuestionResponseDto> questionsAndAnswers) {
        log.info("Starting quiz: {}", quizzId);
        quizStates.put(quizzId, "HAPPENING");

        // Notify all users in the quiz
        Map<Long, WebSocketSession> userSessions = quizUserSessions.getOrDefault(quizzId, Collections.emptyMap());
        userSessions.forEach((userId, session) -> {
            // Simulate sending the quiz questions to clients
            try {
                if (session != null) {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(questionsAndAnswers)));
                }
            } catch (Exception e) {
                log.error("Error sending questions to user {}: {}", userId, e.getMessage());
            }
        });
    }
}
