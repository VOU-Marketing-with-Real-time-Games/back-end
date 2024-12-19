package com.vou.backend.voucher.controller;

import com.vou.backend.voucher.dto.VoucherDto;
import com.vou.backend.voucher.dto.VoucherResponseDto;
import com.vou.backend.voucher.exception.ExistedVoucherException;
import com.vou.backend.voucher.exception.VoucherNotFoundException;
import com.vou.backend.voucher.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class VoucherController {
    private final VoucherService voucherService;
    @PostMapping
    public ResponseEntity<?> createVoucher(@Valid @RequestBody VoucherDto voucherDTO) throws ExistedVoucherException {
        VoucherResponseDto voucher = voucherService.createVoucher(voucherDTO);
        return new ResponseEntity<>(voucher, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllVouchers(Pageable pageable) {
        Page<VoucherResponseDto> voucherList = voucherService.getAllVouchers(pageable);
        return new ResponseEntity<>(voucherList,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAVoucher(@PathVariable String id) throws VoucherNotFoundException {
        VoucherResponseDto voucher = voucherService.getVoucher(id);
        return new ResponseEntity<>(voucher,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>  updateAVoucher(@PathVariable String id,@Valid @RequestBody VoucherDto voucherDto) throws VoucherNotFoundException {
        VoucherResponseDto voucher = voucherService.updateVoucher(id,voucherDto);
        return new ResponseEntity<>(voucher,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable String id) throws VoucherNotFoundException {
        voucherService.deleteVoucherById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public List<VoucherResponseDto> getUserVouchers(@PathVariable Long userId) {
        return voucherService.getVouchersByUserId(userId);
    }
}
