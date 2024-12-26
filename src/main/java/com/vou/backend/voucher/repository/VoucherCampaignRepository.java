package com.vou.backend.voucher.repository;

import com.vou.backend.voucher.model.VoucherCampaign;
import com.vou.backend.voucher.model.VoucherCampaignId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherCampaignRepository extends JpaRepository<VoucherCampaign, VoucherCampaignId> {
    List<VoucherCampaign> findByIdCampaignId(Long campaignId);
}
