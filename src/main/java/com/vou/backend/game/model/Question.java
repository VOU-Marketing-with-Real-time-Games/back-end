package com.vou.backend.game.model;

import com.vou.backend.user.model.UserAnswer;
import com.vou.backend.user.model.UserItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
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
    private String explain;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    Quiz quiz;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers = new ArrayList<>();

}
