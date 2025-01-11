package com.hcmus.brandservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String field;
    private Boolean enabled;
    private Long userId;
    private Date createdAt;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();

    public void copy(Brand brand) {
        //copy if not null
        if (brand.getName() != null) {
            this.setName(brand.getName());
        }
        if (brand.getField() != null) {
            this.setField(brand.getField());
        }
        if (brand.getEnabled() != null) {
            this.setEnabled(brand.getEnabled());
        }
        if (brand.getUserId() != null) {
            this.setUserId(brand.getUserId());
        }
        if (brand.getCreatedAt() != null) {
            this.setCreatedAt(brand.getCreatedAt());
        }
        if (brand.getBranches() != null) {
            this.setBranches(brand.getBranches());
        }
    }

    public void addBranch(Branch branch) {
        branches.add(branch);
        branch.setBrand(this);
    }

    public void removeBranch(Branch branch) {
        branches.remove(branch);
        branch.setBrand(null);
    }
}

