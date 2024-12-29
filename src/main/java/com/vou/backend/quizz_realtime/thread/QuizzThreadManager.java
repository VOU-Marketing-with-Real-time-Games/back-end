package com.vou.backend.quizz_realtime.thread;

import com.vou.backend.game.quizz.dto.QuestionResponseDto;
import com.vou.backend.game.quizz.model.Quizz;
import com.vou.backend.game.quizz.service.QuestionService;
import com.vou.backend.quizz_realtime.socket.QuizSocketHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

    public void startGame(Quizz quizz, List<QuestionResponseDto> questionResponseDtos) {
        executorService.submit(new QuizzThread(quizz, quizWebSocketHandler, questionResponseDtos));
    }
}
