package com.vou.backend.campaign.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "favourite_campaign_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteCampaignUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    Date addToTime;
}