package com.hcmus.quizzrealtime.thread;

import com.hcmus.quizzrealtime.dto.QuestionDto;
import com.hcmus.quizzrealtime.dto.QuizzDto;
import com.hcmus.quizzrealtime.socket.QuizSocketHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class QuizzThreadManager {
    private final ExecutorService executorService;
    private final QuizSocketHandler quizWebSocketHandler;
    private final ModelMapper modelMapper;
    private final int threadPoolSize = 100;

    public QuizzThreadManager(QuizSocketHandler quizSocketHandler, ModelMapper modelMapper) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize); // Thread pool size
        this.quizWebSocketHandler = quizSocketHandler;
        this.modelMapper = modelMapper;
    }

    public void startGame(QuizzDto quizz, List<QuestionDto> questionResponseDtos) {
        executorService.submit(new QuizzThread(quizz, quizWebSocketHandler, questionResponseDtos));
    }
}
