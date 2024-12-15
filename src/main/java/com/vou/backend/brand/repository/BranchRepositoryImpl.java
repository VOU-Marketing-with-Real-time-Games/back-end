package com.vou.backend.brand.repository;

import com.vou.backend.brand.model.Branch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.locationtech.jts.geom.Point;

import java.util.List;

public class BranchRepositoryImpl implements BranchRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // Find branches within a radius from a center point
    @Override
    public List<Branch> findByLocationWithRadius(Point center, double radius) {
        String query = "SELECT br FROM Branch br " +
                "WHERE ST_Distance_Sphere(br.location, ST_GeomFromText(:center, 4326)) <= :radius";

        return entityManager.createQuery(query, Branch.class)
                .setParameter("center", center.toText())  // Convert Point to WKT format
                .setParameter("radius", radius)
                .getResultList();
    }
}
