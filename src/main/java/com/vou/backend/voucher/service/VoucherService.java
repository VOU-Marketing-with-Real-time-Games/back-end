package com.vou.backend.voucher.service;
import com.vou.backend.voucher.dto.VoucherDto;
import com.vou.backend.voucher.dto.VoucherResponseDto;
import com.vou.backend.voucher.exception.ExistedVoucherException;
import com.vou.backend.voucher.exception.VoucherNotFoundException;
import com.vou.backend.voucher.model.Voucher;
import com.vou.backend.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final ModelMapper modelMapper;
    public VoucherResponseDto createVoucher(VoucherDto voucherDto) throws ExistedVoucherException {
        Optional<Voucher> existedVoucher = voucherRepository.findById(voucherDto.getCode());
        if(!existedVoucher.isEmpty())
            throw new ExistedVoucherException("Existed voucher with code:" + voucherDto.getCode());
        Date current = new Date();
        Voucher voucher = modelMapper.map(voucherDto,Voucher.class);
        voucher.setCreatedAt(current);
        voucher.setStatus("Pending");
        return modelMapper.map(voucherRepository.save(voucher),VoucherResponseDto.class);
    }
    public Page<VoucherResponseDto> getAllVouchers(Pageable pageable) {
        return voucherRepository.findAll(pageable).map(value -> modelMapper.map(value,VoucherResponseDto.class));
    }
    public VoucherResponseDto getVoucher(String id) throws VoucherNotFoundException {
        validateId(id);
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherNotFoundException("Voucher not found with id: " + id));
        return modelMapper.map(voucher,VoucherResponseDto.class);
    }
    public VoucherResponseDto updateVoucher(String id, VoucherDto voucherDto) throws VoucherNotFoundException {
        validateId(id);
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherNotFoundException("Voucher not found with id: " + id));
        modelMapper.map(voucherDto, voucher);
        return modelMapper.map(voucherRepository.save(voucher),VoucherResponseDto.class);
    }
    public void deleteVoucherById(String id) throws VoucherNotFoundException {
        validateId(id);
        getVoucher(id);
        voucherRepository.deleteById(id);
    }
    private void validateId(String id) {
        if (id == null || id.length()==0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
}
