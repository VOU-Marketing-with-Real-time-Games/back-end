package com.vou.backend.brand.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}

