package com.hcmus.brandservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String status;

    @Column(columnDefinition = "POINT SRID 4326", nullable = false)
    private Point location;

    private Boolean enable;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public void copy(Branch branch) {
        if (branch.getName() != null) {
            this.setName(branch.getName());
        }
        if (branch.getAddress() != null) {
            this.setAddress(branch.getAddress());
        }
        if (branch.getStatus() != null) {
            this.setStatus(branch.getStatus());
        }
        if (branch.getLocation() != null) {
            this.setLocation(branch.getLocation());
        }
        if (branch.getEnable() != null) {
            this.setEnable(branch.getEnable());
        }
        if (branch.getBrand() != null) {
            this.setBrand(branch.getBrand());
        }
    }
}
