package com.vou.backend.brand.controller;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vou.backend.brand.dto.BranchRequestDto;
import com.vou.backend.brand.dto.BranchRespondDto;
import com.vou.backend.brand.exception.BranchNotFoundException;
import com.vou.backend.brand.exception.BrandNotFoundException;
import com.vou.backend.brand.model.Branch;
import com.vou.backend.brand.service.BranchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/branches")
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

}
