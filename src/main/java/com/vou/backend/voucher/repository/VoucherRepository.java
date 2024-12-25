package com.vou.backend.voucher.repository;

import com.vou.backend.voucher.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher,String> {
}
