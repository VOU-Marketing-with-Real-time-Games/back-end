package com.vou.backend.game.puzzle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "puzzle")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Puzzle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private String description;

    private Integer itemNum;

    private Long campaignGameId;

    @OneToMany(mappedBy = "puzzle", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    public void copy(Puzzle puzzle) {
        this.image = puzzle.getImage();
        this.name = puzzle.getName();
        this.description = puzzle.getDescription();
        this.itemNum = puzzle.getItemNum();
        this.items = puzzle.getItems();
    }
}
