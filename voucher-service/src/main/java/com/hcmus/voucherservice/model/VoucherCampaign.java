package com.hcmus.voucherservice.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
