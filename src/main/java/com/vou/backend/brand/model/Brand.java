package com.vou.backend.brand.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String status;
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
        if (brand.getStatus() != null) {
            this.setStatus(brand.getStatus());
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

