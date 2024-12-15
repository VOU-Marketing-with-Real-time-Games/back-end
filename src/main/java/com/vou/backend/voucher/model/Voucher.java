package com.vou.backend.voucher.model;

import jakarta.persistence.*;
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

    private Date expired_date;
    private String status;
    private Date createdAt;

    private  Long brandId;
}
