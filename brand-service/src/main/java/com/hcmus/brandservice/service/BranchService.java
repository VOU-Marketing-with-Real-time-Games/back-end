package com.hcmus.brandservice.service;

import com.hcmus.brandservice.dto.BranchRequestDto;
import com.hcmus.brandservice.dto.BranchRespondDto;
import com.hcmus.brandservice.exception.BranchNotFoundException;
import com.hcmus.brandservice.exception.BrandNotFoundException;
import com.hcmus.brandservice.model.Branch;
import com.hcmus.brandservice.model.Brand;
import com.hcmus.brandservice.repository.BranchRepository;
import com.hcmus.brandservice.repository.BrandRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<BranchRespondDto> findAll() {
        return branchRepository.findAll()
                .stream()
                .map(
                        branch -> BranchRespondDto.toBranchRespondDto(branch))
                .toList();
    }

    public BranchRespondDto findById(Long id) throws BranchNotFoundException {
        return branchRepository.findById(id)
                .map(
                        branch -> modelMapper.map(branch, BranchRespondDto.class))
                .orElseThrow(
                        () -> new BranchNotFoundException("Branch with id " + id + " not found"));
    }

    public BranchRespondDto update(Long id, BranchRequestDto branchDto)
            throws BranchNotFoundException, BrandNotFoundException {
        Branch branch = toBranch(branchDto);
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(
                        () -> new BranchNotFoundException("Branch with id " + id + " not found"));
        existingBranch.copy(branch);
        Brand brand = brandRepository.findById(branch.getBrand()
                        .getId())
                .orElseThrow(
                        () -> new BrandNotFoundException("Brand with id " + branch.getBrand()
                                .getId() + " not found"));
        brand.removeBranch(existingBranch);
        brand.addBranch(existingBranch);
        branch.setBrand(brand);
        return modelMapper.map(branchRepository.save(existingBranch), BranchRespondDto.class);
    }

    public BranchRespondDto create(BranchRequestDto branchDto) throws BrandNotFoundException {
        Branch branch = toBranch(branchDto);
        ;
        Brand brand = brandRepository.findById(branch.getBrand()
                        .getId())
                .orElseThrow(
                        () -> new BrandNotFoundException("Brand with id " + branch.getBrand()
                                .getId() + " not found"));
        brand.addBranch(branch);
        branch.setBrand(brand);
        return modelMapper.map(branchRepository.save(branch), BranchRespondDto.class);
    }

    public void delete(Long id) throws BranchNotFoundException {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch with id " + id + " not found"));
        branchRepository.delete(branch);
    }

    public Branch toBranch(BranchRequestDto branchDto) throws BrandNotFoundException {
        Brand brand = brandRepository.findById(branchDto.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + branchDto.getBrandId()));

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(branchDto.getLongtitude(), branchDto.getLatitude());
        Point point = geometryFactory.createPoint(coordinate);

        Branch branch = new Branch();
        branch.setBrand(brand);
        branch.setLocation(point);
        branch.setName(branchDto.getName());
        branch.setAddress(branchDto.getAddress());

        return branch;
    }

    public List<BranchRespondDto> findNearBy(Point center, Double radius) {
        return branchRepository.findByLocationWithRadius(center, radius)
                .stream()
                .map(
                        branch -> modelMapper.map(branch, BranchRespondDto.class))
                .toList();
    }

    public List<BranchRespondDto> findByBrandId(Long brandId) {
        return branchRepository.findByBrandId(brandId).stream()
                .map(
                        branch -> BranchRespondDto.toBranchRespondDto(branch))
                .toList();
    }
}
