package com.vou.backend.user.model;

import com.vou.backend.game.model.Item;
import com.vou.backend.game.model.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    private Boolean isCorrect;
    private Date timeAnswer;
}
