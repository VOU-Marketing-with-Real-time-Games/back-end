package com.vou.backend.brand.repository;

import com.vou.backend.brand.model.Branch;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface BranchRepositoryCustom {
    List<Branch>  findByLocationWithRadius(Point center, double radius);
}
