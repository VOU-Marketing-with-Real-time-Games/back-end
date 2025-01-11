package com.hcmus.brandservice.controller;

import com.hcmus.brandservice.dto.BranchRespondDto;
import com.hcmus.brandservice.dto.BrandRequestDto;
import com.hcmus.brandservice.dto.BrandRespondDto;
import com.hcmus.brandservice.exception.BranchNotFoundException;
import com.hcmus.brandservice.exception.BrandExistedException;
import com.hcmus.brandservice.exception.BrandNotFoundException;
import com.hcmus.brandservice.service.BranchService;
import com.hcmus.brandservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAllBrands() {
        List<BrandRespondDto> brands = brandService.findAll();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBrand(@RequestBody BrandRequestDto brandDto) throws BrandExistedException {
        brandService.create(brandDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable(name = "id") Long id) throws BrandNotFoundException {
        BrandRespondDto br = brandService.findById(id);
        return ResponseEntity.ok(br);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable(name = "id") Long id, @RequestBody BrandRequestDto brandDto)
            throws BrandNotFoundException, BranchNotFoundException {
        BrandRespondDto br = brandService.update(id, brandDto);
        return ResponseEntity.ok(br);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable(name = "id") Long id) throws BrandNotFoundException {
        brandService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
