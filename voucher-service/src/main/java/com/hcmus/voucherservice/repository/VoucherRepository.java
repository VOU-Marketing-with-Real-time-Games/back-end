package com.hcmus.voucherservice.repository;

import com.hcmus.voucherservice.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher,String> {
}
