package com.vou.backend.game.quizz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionName;
    private String image;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String explaination;

    @ManyToOne
    @JoinColumn(name = "quizz_id")
    private Quizz quizz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers = new ArrayList<>();

    public void copy(Question questionDetails) {
        this.questionName = questionDetails.getQuestionName();
        this.image = questionDetails.getImage();
        this.option1 = questionDetails.getOption1();
        this.option2 = questionDetails.getOption2();
        this.option3 = questionDetails.getOption3();
        this.option4 = questionDetails.getOption4();
        this.answer = questionDetails.getAnswer();
        this.explaination = questionDetails.getExplaination();
        this.quizz = questionDetails.getQuizz();
    }
}
