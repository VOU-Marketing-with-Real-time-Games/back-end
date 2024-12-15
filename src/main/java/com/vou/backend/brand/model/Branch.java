package com.vou.backend.brand.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
