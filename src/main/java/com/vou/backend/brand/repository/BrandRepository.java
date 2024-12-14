package com.vou.backend.brand.repository;

import com.vou.backend.brand.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BrandRepository extends JpaRepository<Brand,Long> {
}
