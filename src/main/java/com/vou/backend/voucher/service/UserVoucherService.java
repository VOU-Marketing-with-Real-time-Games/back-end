package com.vou.backend.voucher.service;

import com.vou.backend.voucher.model.Voucher;
import com.vou.backend.voucher.model.VoucherUser;
import com.vou.backend.voucher.repository.VoucherUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserVoucherService {
    private final VoucherUserRepository userVoucherRepository;

    public void addVoucherToUser(Long userId, Voucher voucher) {
        VoucherUser voucherUser = VoucherUser.builder()
                .userId(userId)
                .voucher(voucher)
                .status("ACTIVE")
                .addToTime(new Date())
                .build();
        userVoucherRepository.save(voucherUser);
    }

}
