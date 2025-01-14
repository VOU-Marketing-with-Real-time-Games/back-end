package com.hcmus.brandservice.controller;

import com.hcmus.brandservice.dto.BrandRequestDto;
import com.hcmus.brandservice.dto.BrandRespondDto;
import com.hcmus.brandservice.dto.BrandStatisticsDto;
import com.hcmus.brandservice.exception.BranchNotFoundException;
import com.hcmus.brandservice.exception.BrandExistedException;
import com.hcmus.brandservice.exception.BrandNotFoundException;
import com.hcmus.brandservice.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/v3/brands")
public class BrandController {
    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAllBrands() {
        logger.info("Fetching all brands");
        List<BrandRespondDto> brands = brandService.findAll();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBrand(@RequestBody BrandRequestDto brandDto) throws BrandExistedException {
        logger.info("Creating a new brand with data: {}", brandDto);
        brandService.create(brandDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBrand(@RequestParam(name = "name") String name) {
        logger.info("Searching brand with name: {}", name);
        List<BrandRespondDto> brands = brandService.search(name);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable(name = "id") Long id) throws BrandNotFoundException {
        logger.info("Fetching brand with id: {}", id);
        BrandRespondDto br = brandService.findById(id);
        return ResponseEntity.ok(br);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable(name = "id") Long id, @RequestBody BrandRequestDto brandDto)
            throws BrandNotFoundException, BranchNotFoundException {
        logger.info("Updating brand with id: {} with data: {}", id, brandDto);
        BrandRespondDto br = brandService.update(id, brandDto);
        return ResponseEntity.ok(br);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable(name = "id") Long id) throws BrandNotFoundException {
        logger.info("Deleting brand with id: {}", id);
        brandService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/statistics")
public ResponseEntity<BrandStatisticsDto> getBrandStatistics() {
    BrandStatisticsDto stats = brandService.getBrandStatistics();
    return ResponseEntity.ok(stats);
}
}
