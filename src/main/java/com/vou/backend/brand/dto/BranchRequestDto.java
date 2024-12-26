package com.vou.backend.brand.dto;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.vou.backend.brand.exception.BrandNotFoundException;
import com.vou.backend.brand.model.Branch;
import com.vou.backend.brand.model.Brand;
import com.vou.backend.brand.repository.BrandRepository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private Float longtitude;
    @NotNull(message = "Latitude is required")
    private Float latitude;
}
