package com.hcmus.voucherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherUserRequestDto {
    private Long campaignId;
    private List<Long> userIds;
}
