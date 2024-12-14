package com.vou.backend.user.model;

import com.vou.backend.campaign.model.Campaign;
import com.vou.backend.game.model.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private Integer totalItem;
}
