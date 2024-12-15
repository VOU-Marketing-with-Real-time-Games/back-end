package com.vou.backend.game.puzzle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer position;
    private String description;
    private Integer total;
    private Integer remainingNum;

    @ManyToOne
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;
}
