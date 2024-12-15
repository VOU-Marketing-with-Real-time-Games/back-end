package com.vou.backend.voucher.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherCampaignId implements Serializable {
    private Long campaignId;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
}
