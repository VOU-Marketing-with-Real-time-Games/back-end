package com.vou.backend.voucher.model;

import com.vou.backend.campaign.model.Campaign;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "voucher_campaign")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherCampaign {
    @EmbeddedId
    private VoucherCampaignId id;

    private Integer total;
    private Integer remaining;
}
