package com.hcmus.brandservice.service;

import com.hcmus.brandservice.dto.BrandDailyCountDto;
import com.hcmus.brandservice.dto.BrandRequestDto;
import com.hcmus.brandservice.dto.BrandRespondDto;
import com.hcmus.brandservice.dto.BrandStatisticsDto;
import com.hcmus.brandservice.exception.BranchNotFoundException;
import com.hcmus.brandservice.exception.BrandExistedException;
import com.hcmus.brandservice.exception.BrandNotFoundException;
import com.hcmus.brandservice.model.Branch;
import com.hcmus.brandservice.model.Brand;
import com.hcmus.brandservice.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<BrandRespondDto> search(String name) {
        return brandRepository.findByNameContaining(name)
                .stream().map(
                        brand -> modelMapper.map(brand, BrandRespondDto.class))
                .toList();
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
    public BrandStatisticsDto getBrandStatistics() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Integer> dailyBrandCounts = new ArrayList<>();
        int totalBrands = 0;
        int previousDayCount = 0;
        boolean isTrendUp = false;

        for (int i = 0; i < 30; i++) {
            LocalDate date = startDate.plusDays(i);
            String formattedDate = date.format(formatter);
            int brandCount = brandRepository.countBrandsByDate(formattedDate);
            dailyBrandCounts.add(brandCount);
            totalBrands += brandCount;

            if (i > 0 && brandCount > previousDayCount) {
                isTrendUp = true;
            }
            previousDayCount = brandCount;
        }

        BrandStatisticsDto stats = new BrandStatisticsDto();
        stats.setTitle("Brands");
        stats.setValue(String.valueOf(totalBrands));
        stats.setInterval("Last 30 days");
        stats.setTrend(isTrendUp ? "up" : "down");
        stats.setData(dailyBrandCounts);

        return stats;
    }

    public List<BrandDailyCountDto> getBrandDailyCounts() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<BrandDailyCountDto> dailyCounts = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            LocalDate date = startDate.plusDays(i);
            String formattedDate = date.format(formatter);
            int brandCount = brandRepository.countBrandsByDate(formattedDate);
            dailyCounts.add(new BrandDailyCountDto(brandCount, formattedDate));
        }
        return dailyCounts;
    }

}
