package com.hcmus.brandservice.repository;

import com.hcmus.brandservice.model.Branch;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface BranchRepositoryCustom {
    List<Branch>  findByLocationWithRadius(Point center, double radius);
}
