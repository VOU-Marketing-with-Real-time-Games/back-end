package com.vou.backend.voucher.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherDto {
    @NotBlank(message = "Voucher code must not be empty")
    private String code;
    @NotBlank(message = "Image URL must not be empty")
    private String image;
    @NotNull(message = "Discount value must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount must not exceed 100")
    private Double discount;
    @NotBlank(message = "Description must not be empty")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    @NotNull(message = "Expired date must not be null")
    @Future(message = "Expired date must be in the future")
    private Date expiredDate;
    @NotNull(message = "Brand id value must not be null")
    private  Long brandId;
}
