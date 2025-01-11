package com.hcmus.brandservice.dto;

import com.hcmus.brandservice.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BranchRespondDto {
    private Long id;
    private Long brandId;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public static BranchRespondDto toBranchRespondDto(Branch branch) {
        return new BranchRespondDto(
                branch.getId(),
                branch.getBrand().getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getLocation().getY(),
                branch.getLocation().getX());
    }
}
