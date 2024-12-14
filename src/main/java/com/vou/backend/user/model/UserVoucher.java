package com.vou.backend.user.model;

import com.vou.backend.game.model.Item;
import com.vou.backend.voucher.model.Voucher;
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
public class UserVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    private  Date addToTime;
    private String status;
}
