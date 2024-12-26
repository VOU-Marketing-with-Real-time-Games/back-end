package com.vou.backend.brand.dto;


import java.util.List;

import com.vou.backend.brand.model.Brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandRespondDto {
    private Long id;
    private String name;
    private String field;
    private String status;
    private Boolean enabled;
    private Long creator;
    private String createdAt;
}
