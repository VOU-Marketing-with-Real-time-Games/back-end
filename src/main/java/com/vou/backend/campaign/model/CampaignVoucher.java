package com.vou.backend.campaign.model;

import com.vou.backend.game.model.Item;
import com.vou.backend.voucher.model.Voucher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "campaign_voucher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    private Integer total;
    private Integer remaining;
}
