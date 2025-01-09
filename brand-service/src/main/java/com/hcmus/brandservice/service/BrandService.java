package com.hcmus.brandservice.service;

import com.hcmus.brandservice.dto.BrandRequestDto;
import com.hcmus.brandservice.dto.BrandRespondDto;
import com.hcmus.brandservice.exception.BranchNotFoundException;
import com.hcmus.brandservice.exception.BrandExistedException;
import com.hcmus.brandservice.exception.BrandNotFoundException;
import com.hcmus.brandservice.model.Branch;
import com.hcmus.brandservice.model.Brand;
import com.hcmus.brandservice.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<BrandRespondDto> findAll() {
        return brandRepository.findAll()
                .stream().map(
                        brand -> modelMapper.map(brand, BrandRespondDto.class))
                .toList();
    }

    public BrandRespondDto findById(Long id) throws BrandNotFoundException {
        return brandRepository.findById(id).map(
                brand -> modelMapper.map(brand, BrandRespondDto.class)).orElseThrow(
                        () -> new BrandNotFoundException("Brand with id " + id + " not found"));
    }

    public BrandRespondDto update(Long id, BrandRequestDto brandDto) throws BrandNotFoundException, BranchNotFoundException {
        Brand brand = modelMapper.map(brandDto,Brand.class);
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(
                        () -> new BrandNotFoundException("Brand with id " + id + " not found"));
        existingBrand.copy(brand);
        List<Branch> branches = existingBrand.getBranches();
        for (Branch branch : branches) {
            branch.setBrand(existingBrand);
        }
        return modelMapper.map(brandRepository.save(existingBrand), BrandRespondDto.class);
    }

    public BrandRespondDto create(BrandRequestDto brandDto) throws BrandExistedException {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        if (brandRepository.findByName(brand.getName()) != null) {
            throw new BrandExistedException("Brand existed");
        }
        brand.setCreatedAt(new Date());
        brand.setEnabled(true);
        return modelMapper.map(brandRepository.save(brand), BrandRespondDto.class);
    }

    public void delete(Long id) throws BrandNotFoundException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand with id " + id + " not found"));
        brandRepository.delete(brand);
    }

    public Brand getById(Long id) throws BrandNotFoundException {
        return brandRepository.findById(id).orElseThrow(
                () -> new BrandNotFoundException("Brand with id " + id + " not found"));
    }
}
