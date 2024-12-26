package com.vou.backend.brand.repository;

import com.vou.backend.brand.model.Brand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface BrandRepository extends JpaRepository<Brand,Long> {
    @Query ("SELECT b FROM Brand b WHERE b.name = :name")
    Brand findByName(String name);

    @Query("SELECT b FROM Brand b WHERE b.field = :field")
    List<Brand> findByField(String field);
}
