package com.vou.backend.voucher.repository;

import com.vou.backend.voucher.model.Voucher;
import com.vou.backend.voucher.model.VoucherCampaign;
import com.vou.backend.voucher.model.VoucherCampaignId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherCampaignRepository extends JpaRepository<VoucherCampaign, VoucherCampaignId> {
    List<VoucherCampaign> findByIdCampaignId(Long campaignId);

    @Query("select vc from VoucherCampaign vc where vc.id.campaignId = :campaignId and vc.id.voucher.code = :voucherCode")
    VoucherCampaign findByCampaignIdAndVoucherId(Long campaignId, String voucherCode);

    @Query("select vc from VoucherCampaign vc where vc.id.campaignId = :campaignId")
    List<VoucherCampaign> findByCampaignId(Long campaignId);
}
