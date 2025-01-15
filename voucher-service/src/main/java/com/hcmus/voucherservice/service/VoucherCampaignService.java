package com.hcmus.voucherservice.service;

import com.hcmus.voucherservice.dto.VoucherResponseDto;
import com.hcmus.voucherservice.dto.VoucherUserRequestDto;
import com.hcmus.voucherservice.model.Voucher;
import com.hcmus.voucherservice.model.VoucherCampaign;
import com.hcmus.voucherservice.repository.VoucherCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherCampaignService {
    private final VoucherCampaignRepository voucherCampaignRepository;
    private final UserVoucherService userVoucherService;

    public void updateRemainingNum(Long campaignId, String voucherCode) {
        VoucherCampaign voucherCampaign = voucherCampaignRepository.findByCampaignIdAndVoucherId(campaignId, voucherCode);
        voucherCampaign.setRemaining(voucherCampaign.getRemaining() - 1);
        voucherCampaignRepository.save(voucherCampaign);
    }

    public List<String> getDistinctVoucherIdsByCampaignIds(List<Long> campaignIds) {
        return voucherCampaignRepository.findDistinctVoucherIdsByCampaignIds(campaignIds);
    }



    public boolean takeVoucherToUser(Long campaignId, Long userId) {
        List<VoucherCampaign> voucherCampaigns = voucherCampaignRepository.findByCampaignId(campaignId);
        for(VoucherCampaign voucherCampaign : voucherCampaigns) {
            if(voucherCampaign.getRemaining() > 0) {
                Voucher voucher = voucherCampaign.getId().getVoucher();
                userVoucherService.addVoucherToUser(userId, voucher);
                updateRemainingNum(campaignId, voucher.getCode());
                return true;
            }
        }
        return false;
    }

    public void takeVoucherAfterQuiz(VoucherUserRequestDto voucherUserRequestDto) {
        Long campaignId = voucherUserRequestDto.getCampaignId();
        List<Long> userIds = voucherUserRequestDto.getUserIds();
        for(Long userId : userIds) {
            takeVoucherToUser(campaignId, userId);
        }
    }


    public int getNumberOfVouchersGivenToUserByCampaignId(Long campaignId) {
        return voucherCampaignRepository.findByCampaignId(campaignId)
                .stream()
                .mapToInt(vc -> vc.getTotal() - vc.getRemaining())
                .sum();
    }

}
