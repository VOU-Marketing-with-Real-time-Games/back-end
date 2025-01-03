package com.hcmus.voucherservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "voucher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher {
    @Id
    private String code;
    private String qrCode;
    private String image;
    private Double discount;
    @Column(length = 1000)
    private String description;
    private Date expiredDate;
    private String status;
    private Date createdAt;
    private  Long brandId;
}
