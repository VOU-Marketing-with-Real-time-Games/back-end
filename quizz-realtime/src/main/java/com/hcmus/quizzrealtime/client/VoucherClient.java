package com.hcmus.quizzrealtime.client;

import com.hcmus.quizzrealtime.dto.VoucherUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "voucher-service", url = "http://localhost:8006/v3/vouchers")
public interface VoucherClient {
    @GetMapping("/take-voucher-after-quizz")
    ResponseEntity<Void> takeVoucherAfterQuizz(@RequestBody VoucherUserRequestDto voucherUserRequestDto);
}
