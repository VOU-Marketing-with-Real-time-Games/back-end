
package com.hcmus.brandservice.repository;


import com.hcmus.brandservice.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Long>, BranchRepositoryCustom {
    @Query ("SELECT b FROM Branch b WHERE b.name = :name")
    Branch findByName(String name);
}
