package com.vou.backend.campaign.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "campaign")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String filedId;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdAt;
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<FavoriteCampaignUser> favouriteCampaigns = new ArrayList<>();

}
