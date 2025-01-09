package com.hcmus.quizzrealtime.thread;

import com.hcmus.quizzrealtime.dto.QuestionDto;
import com.hcmus.quizzrealtime.dto.QuizzDto;
import com.hcmus.quizzrealtime.socket.QuizSocketHandler;

import java.util.List;

public class QuizzThread implements Runnable {
    private QuizzDto quizz;
    private QuizSocketHandler quizSocketHandler;
    private List<QuestionDto> questionResponseDto;

    public QuizzThread(QuizzDto quizz, QuizSocketHandler quizWebSocketHandler, List<QuestionDto> questionResponseDto) {
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
