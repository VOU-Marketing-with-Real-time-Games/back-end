package com.vou.backend.voucher.service;

import com.vou.backend.voucher.model.Voucher;
import com.vou.backend.voucher.model.VoucherCampaign;
import com.vou.backend.voucher.model.VoucherUser;
import com.vou.backend.voucher.repository.VoucherCampaignRepository;
import com.vou.backend.voucher.repository.VoucherUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherCampaignService {
    private final VoucherCampaignRepository voucherCampaignRepository;
    private final UserVoucherService userVoucherService;

    public void updateRemainingNum(Long campaignId, String voucherCode) {
        VoucherCampaign voucherCampaign = voucherCampaignRepository.findByCampaignIdAndVoucherId(campaignId, voucherCode);
        voucherCampaign.setRemaining(voucherCampaign.getRemaining() - 1);
    }

    public void takeVoucherToUser(Long campaignId, Long userId) {
        List<VoucherCampaign> voucherCampaigns = voucherCampaignRepository.findByCampaignId(campaignId);
        for(VoucherCampaign voucherCampaign : voucherCampaigns) {
            if(voucherCampaign.getRemaining() > 0) {
                Voucher voucher = voucherCampaign.getId().getVoucher();
                userVoucherService.addVoucherToUser(userId, voucher);
                updateRemainingNum(campaignId, voucher.getCode());
            }
        }
    }
}
