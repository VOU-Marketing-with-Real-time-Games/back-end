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
    @Column(columnDefinition = "POINT SRID 4326", nullable = false)
    private Point location;
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
        if (branch.getLocation() != null) {
            this.setLocation(branch.getLocation());
        }
        if (branch.getBrand() != null) {
            this.setBrand(branch.getBrand());
        }
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", brand=" + brand +
                '}';
    }
}
