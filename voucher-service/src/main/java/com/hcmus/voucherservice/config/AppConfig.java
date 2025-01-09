package com.hcmus.voucherservice.config;

import com.hcmus.voucherservice.dto.UpdateVoucherDto;
import com.hcmus.voucherservice.dto.VoucherDto;
import com.hcmus.voucherservice.dto.VoucherResponseDto;
import com.hcmus.voucherservice.model.Voucher;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    /**
     * Configures and returns a ModelMapper bean.
     *
     * @return the configured ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configVoucherConverters(modelMapper);
        return modelMapper;
    }


    private void configVoucherConverters(ModelMapper modelMapper)
    {
        // DTO to Model mapping
        modelMapper.typeMap(VoucherDto.class, Voucher.class).addMappings(mapper -> {
            mapper.skip(Voucher::setQrCode);
            mapper.skip(Voucher::setCreatedAt);
            mapper.skip(Voucher::setStatus);
        }).setPostConverter(context -> {
            Voucher voucher = context.getDestination();
            if (voucher.getStatus() == null) {
                voucher.setStatus("Pending");
            }
            return voucher;
        });
        // Model to Response DTO mapping
        modelMapper.typeMap(Voucher.class, VoucherResponseDto.class).addMappings(mapper -> {
            mapper.map(Voucher::getCode, VoucherResponseDto::setCode);
            mapper.map(Voucher::getImage, VoucherResponseDto::setImage);
            mapper.map(Voucher::getQrCode, VoucherResponseDto::setQrCode);
            mapper.map(Voucher::getCreatedAt, VoucherResponseDto::setCreatedAt);
            mapper.map(Voucher::getDescription, VoucherResponseDto::setDescription);
            mapper.map(Voucher::getBrandId, VoucherResponseDto::setBrandId);
            mapper.map(Voucher::getDiscount, VoucherResponseDto::setDiscount);
            mapper.map(Voucher::getExpiredDate,VoucherResponseDto::setExpiredDate);
        });
        //Update Dto to model mapping
        modelMapper.typeMap(UpdateVoucherDto.class, Voucher.class).addMappings(mapper -> {
            mapper.skip(Voucher::setQrCode);
            mapper.skip(Voucher::setCreatedAt);
        });
    }
}
