package com.vou.backend.game.model;

import com.vou.backend.brand.model.Branch;
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
    @OneToMany(mappedBy = "puzzle", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
}
