package com.vou.backend.quizz_realtime.thread;

import com.vou.backend.game.quizz.dto.QuestionResponseDto;
import com.vou.backend.game.quizz.model.Quizz;
import com.vou.backend.quizz_realtime.socket.QuizSocketHandler;

import java.util.List;

public class QuizzThread implements Runnable {
    private Quizz quizz;
    private QuizSocketHandler quizSocketHandler;
    private List<QuestionResponseDto> questionResponseDto;

    public QuizzThread(Quizz quizz, QuizSocketHandler quizWebSocketHandler, List<QuestionResponseDto> questionResponseDto) {
        this.quizz = quizz;
        this.quizSocketHandler = quizWebSocketHandler;
        this.questionResponseDto = questionResponseDto;
    }

    @Override
    public void run()
    {
        quizSocketHandler.startQuiz(quizz.getId(), questionResponseDto);
    }
}
