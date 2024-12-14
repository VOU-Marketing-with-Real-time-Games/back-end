package com.vou.backend.voucher.model;

import com.vou.backend.campaign.model.CampaignVoucher;
import com.vou.backend.user.model.UserItem;
import com.vou.backend.user.model.UserVoucher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "voucher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String qrCode;
    private String image;
    private Double discount;
    private String description;
    private String expired_date;
    private String status;
    private Date createdAt;
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<UserVoucher> userVouchers = new ArrayList<>();
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<CampaignVoucher> campaignVouchers = new ArrayList<>();
}
