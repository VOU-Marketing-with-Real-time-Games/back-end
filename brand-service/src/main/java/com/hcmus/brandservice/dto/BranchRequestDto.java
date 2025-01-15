package com.hcmus.brandservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BranchRequestDto {
    @NotNull(message = "Brand id is required")
    private Long brandId;
    @NotNull(message = "Name is required")
    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    private String address;
    @NotNull(message = "Longitude is required")
    private Float longitude;
    @NotNull(message = "Latitude is required")
    private Float latitude;
}
