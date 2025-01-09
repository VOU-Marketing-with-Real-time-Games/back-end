package com.hcmus.brandservice.repository;

import com.hcmus.brandservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface BrandRepository extends JpaRepository<Brand,Long> {
    @Query ("SELECT b FROM Brand b WHERE b.name = :name")
    Brand findByName(String name);

    @Query("SELECT b FROM Brand b WHERE b.field = :field")
    List<Brand> findByField(String field);
}
