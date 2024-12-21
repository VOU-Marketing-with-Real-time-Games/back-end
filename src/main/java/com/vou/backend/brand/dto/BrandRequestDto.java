package com.vou.backend.brand.dto;

import java.util.List;

import com.vou.backend.brand.model.Brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

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

    public Brand toBrand() {
        Brand brand = new Brand();
        brand.setName(this.name);
        brand.setField(this.field);
        brand.setEnabled(true);
        return brand;
    }
}
