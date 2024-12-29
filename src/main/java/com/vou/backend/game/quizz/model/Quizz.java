package com.vou.backend.game.quizz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quizz")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Quizz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Integer secondPerQuestion;
    private Date startTime;
    private Long campaignGameId;


    @OneToMany(mappedBy = "quizz", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public void copy(Quizz quizz) {
        this.name = quizz.getName();
        this.description = quizz.getDescription();
        this.startTime = quizz.getStartTime();
        this.secondPerQuestion = quizz.getSecondPerQuestion();
    }
}
