package com.hcmus.voucherservice.service;

import com.hcmus.voucherservice.dto.UpdateVoucherDto;
import com.hcmus.voucherservice.dto.VoucherDto;
import com.hcmus.voucherservice.dto.VoucherResponseDto;
import com.hcmus.voucherservice.exception.ExistedVoucherException;
import com.hcmus.voucherservice.exception.VoucherNotFoundException;
import com.hcmus.voucherservice.model.Voucher;
import com.hcmus.voucherservice.model.VoucherCampaign;
import com.hcmus.voucherservice.model.VoucherUser;
import com.hcmus.voucherservice.repository.VoucherCampaignRepository;
import com.hcmus.voucherservice.repository.VoucherRepository;
import com.hcmus.voucherservice.repository.VoucherUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherUserRepository voucherUserRepository;
    private final VoucherCampaignRepository voucherCampaignRepository;
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
    public VoucherResponseDto updateVoucher(String id, UpdateVoucherDto voucherDto) throws VoucherNotFoundException {
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
    public List<VoucherResponseDto> getVouchersByUserId(Long userId) {
        List<VoucherUser> voucherUsers = voucherUserRepository.findByUserId(userId);
        return voucherUsers.stream()
                .map(voucherUser -> modelMapper.map(voucherUser.getVoucher(), VoucherResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<VoucherResponseDto> getVouchersByCampaignId(Long campaignId) {
        List<VoucherCampaign> voucherCampaigns = voucherCampaignRepository.findByIdCampaignId(campaignId);
        return voucherCampaigns.stream()
                .map(voucherCampaign -> modelMapper.map(voucherCampaign.getId().getVoucher(), VoucherResponseDto.class))
                .collect(Collectors.toList());
    }
}
