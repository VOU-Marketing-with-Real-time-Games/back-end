package com.vou.backend.voucher.repository;

import com.vou.backend.voucher.model.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherUserRepository extends JpaRepository<VoucherUser, Long> {
    List<VoucherUser> findByUserId(Long userId);
}