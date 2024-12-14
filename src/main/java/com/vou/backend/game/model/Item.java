package com.vou.backend.game.model;

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
    Puzzle puzzle;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<UserItem> userItems = new ArrayList<>();
}
