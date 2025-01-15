package com.hcmus.brandservice.repository;

import com.hcmus.brandservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface BrandRepository extends JpaRepository<Brand,Long> {
    @Query ("SELECT b FROM Brand b WHERE b.name = :name")
    Brand findByName(String name);

    @Query("SELECT b FROM Brand b WHERE b.field = :field")
    List<Brand> findByField(String field);

    @Query("SELECT b FROM Brand b WHERE b.name LIKE %:name%")
    List<Brand> findByNameContaining(String name);
  
    @Query("SELECT COUNT(b) FROM Brand b WHERE DATE_FORMAT(b.createdAt, '%Y-%m-%d') = :date")
    int countBrandsByDate(@Param("date") String date);

    @Query("SELECT b FROM Brand b WHERE b.userId = :userId")
    Brand findByUserId(@Param("userId") Long userId);
}
