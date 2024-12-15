package com.vou.backend.voucher.model;

import com.vou.backend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_voucher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    private  Date addToTime;
    private String status;
}
