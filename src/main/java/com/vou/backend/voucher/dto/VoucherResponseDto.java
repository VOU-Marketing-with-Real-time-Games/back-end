package com.vou.backend.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherResponseDto {
    private String code;
    private String qrCode;
    private String image;
    private Double discount;
    private String description;
    private Date expiredDate;
    private String status;
    private Date createdAt;
    private  Long brandId;
}
