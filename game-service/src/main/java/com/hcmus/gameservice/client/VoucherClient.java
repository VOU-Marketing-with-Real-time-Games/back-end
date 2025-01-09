package com.hcmus.gameservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "voucher-service", url = "http://localhost:8006/v3/vouchers")
public interface VoucherClient {
    @PostMapping("/take-voucher")
    boolean takeVoucherToUser(@RequestParam("campaignId") Long campaignId, @RequestParam("userId") Long userId);
}
