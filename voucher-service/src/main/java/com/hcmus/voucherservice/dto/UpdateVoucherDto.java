package com.hcmus.voucherservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateVoucherDto {
    private String image;
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount must not exceed 100")
    private Double discount;
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    @Future(message = "Expired date must be in the future")
    private Date expiredDate;
    private String status;
}
