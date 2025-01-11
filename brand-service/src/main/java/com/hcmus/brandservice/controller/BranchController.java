package com.hcmus.brandservice.controller;

import com.hcmus.brandservice.dto.BranchRequestDto;
import com.hcmus.brandservice.dto.BranchRespondDto;
import com.hcmus.brandservice.exception.BranchNotFoundException;
import com.hcmus.brandservice.exception.BrandNotFoundException;
import com.hcmus.brandservice.service.BranchService;
import jakarta.validation.Valid;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/branches")
public class BranchController {
    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<?> getAllBrands() {
        logger.info("Fetching all branches");
        List<BranchRespondDto> branches = branchService.findAll();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBranch(@RequestBody @Valid BranchRequestDto branchDto)
            throws BrandNotFoundException {
        logger.info("Creating a new branch with data: {}", branchDto);
        BranchRespondDto br = branchService.create(branchDto);
        return ResponseEntity.ok(br);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable(name = "id") Long id) throws BranchNotFoundException {
        logger.info("Fetching branch with id: {}", id);
        BranchRespondDto br = branchService.findById(id);
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

    @GetMapping("/nearby/{lat}/{lng}/{rad}")
    public ResponseEntity<?> getNearbyBranch(@PathVariable(name = "lat") Double lat,
            @PathVariable(name = "lng") Double lng, @PathVariable(name = "rad") Double rad) {
        logger.info("Fetching nearby branches with latitude: {}, longitude: {}, radius: {}", lat, lng, rad);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(lng, lat);
        Point point = geometryFactory.createPoint(coordinate);
        List<BranchRespondDto> branches = branchService.findNearBy(point, rad);
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable(name = "id") Long id,
            @RequestBody @Valid BranchRequestDto branchDto)
            throws BranchNotFoundException, BrandNotFoundException {
        logger.info("Updating branch with id: {} with data: {}", id, branchDto);
        BranchRespondDto br = branchService.update(id, branchDto);
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable(name = "id") Long id) throws BranchNotFoundException {
        logger.info("Deleting branch with id: {}", id);
        branchService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<?> getBranchesByBrandId(@PathVariable(name = "brandId") Long brandId) {
        logger.info("Fetching branches for brand id: {}", brandId);
        List<BranchRespondDto> branches = branchService.findByBrandId(brandId);
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }
}