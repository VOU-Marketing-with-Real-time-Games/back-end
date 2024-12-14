package com.vou.backend.game.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quiz")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Integer totalTime;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> items = new ArrayList<>();
}
