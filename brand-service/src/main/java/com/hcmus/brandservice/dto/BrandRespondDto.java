package com.hcmus.brandservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
