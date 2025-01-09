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
    private String status;
    private Double longitude;
    private Double lattitude;

    public static BranchRespondDto toBranchRespondDto(Branch branch) {
        BranchRespondDto branchRespondDto = new BranchRespondDto(
                branch.getId(),
                branch.getBrand().getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getStatus(),
                branch.getLocation().getX(),
                branch.getLocation().getY());
        return branchRespondDto;
    }
}
