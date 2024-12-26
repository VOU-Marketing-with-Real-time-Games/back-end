package com.vou.backend.brand.controller;

import java.util.List;

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

import com.vou.backend.brand.dto.BrandRequestDto;
import com.vou.backend.brand.dto.BrandRespondDto;
import com.vou.backend.brand.exception.BranchNotFoundException;
import com.vou.backend.brand.exception.BrandExistedException;
import com.vou.backend.brand.exception.BrandNotFoundException;
import com.vou.backend.brand.model.Brand;
import com.vou.backend.brand.service.BrandService;

@RestController
@RequestMapping("/brands")
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
