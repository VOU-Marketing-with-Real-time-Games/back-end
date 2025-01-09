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
public class BrandRequestDto {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @NotNull
    @NotBlank
    private String field;
}
