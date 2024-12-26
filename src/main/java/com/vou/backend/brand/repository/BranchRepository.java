
package com.vou.backend.brand.repository;

import com.vou.backend.brand.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Long>, BranchRepositoryCustom {
    @Query ("SELECT b FROM Branch b WHERE b.name = :name")
    Branch findByName(String name);
}
