package com.vou.backend.campaign.model;
import com.vou.backend.user.model.UserFavoriteCampaign;
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
    private List<UserFavoriteCampaign> favouriteCampaigns = new ArrayList<>();
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<CampaignVoucher> campaignVouchers = new ArrayList<>();
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<CampaignGame> campaignGames = new ArrayList<>();
}
