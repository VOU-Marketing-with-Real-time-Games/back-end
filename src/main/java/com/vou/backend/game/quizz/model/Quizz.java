package com.vou.backend.game.quizz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quizz")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quizz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Integer secondPerQuestion;
    private Long campaignGameId;

    @OneToMany(mappedBy = "quizz", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public void copy(Quizz quizz) {
        this.name = quizz.getName();
        this.description = quizz.getDescription();
        this.secondPerQuestion = quizz.getSecondPerQuestion();
    }
}
