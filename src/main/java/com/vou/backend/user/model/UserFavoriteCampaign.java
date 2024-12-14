package com.vou.backend.user.model;


import com.vou.backend.campaign.model.Campaign;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_favourite_campaign")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFavoriteCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    Date addToTime;
}