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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/branches")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<?> getAllBrands() {
        List<BranchRespondDto> branchs = branchService.findAll();
        return new ResponseEntity<>(branchs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBranch(@RequestBody @Valid BranchRequestDto branchDto)
            throws BrandNotFoundException {
        BranchRespondDto br = branchService.create(branchDto);
        return ResponseEntity.ok(br);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable(name = "id") Long id) throws BranchNotFoundException {
        BranchRespondDto br = branchService.findById(id);
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

    @GetMapping("/nearby/{lat}/{lng}/{rad}")
    public ResponseEntity<?> getNearbyBranch(@PathVariable(name = "lat") Double lat,
            @PathVariable(name = "lng") Double lng, @PathVariable(name = "rad") Double rad) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(lng, lat);
        Point point = geometryFactory.createPoint(coordinate);
        List<BranchRespondDto> branchs = branchService.findNearBy(point, rad);
        return new ResponseEntity<>(branchs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable(name = "id") Long id,
            @RequestBody @Valid BranchRequestDto branchDto)
            throws BranchNotFoundException, BrandNotFoundException {
        BranchRespondDto br = branchService.update(id, branchDto);
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable(name = "id") Long id) throws BranchNotFoundException {
        branchService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<?> getBranchesByBrandId(@PathVariable(name = "brandId") Long brandId) {
        List<BranchRespondDto> branches = branchService.findByBrandId(brandId);
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }
}
