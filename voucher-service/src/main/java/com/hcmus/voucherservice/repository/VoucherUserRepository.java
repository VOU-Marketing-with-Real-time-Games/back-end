package com.hcmus.voucherservice.repository;

import com.hcmus.voucherservice.model.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherUserRepository extends JpaRepository<VoucherUser, Long> {
    List<VoucherUser> findByUserId(Long userId);
}