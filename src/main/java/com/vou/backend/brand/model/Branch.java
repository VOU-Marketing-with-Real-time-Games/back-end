package com.vou.backend.brand.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.awt.*;

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
    private String field;
    private String address;
    private Enum status;
    private Point location;
    private Boolean enable;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;
}
