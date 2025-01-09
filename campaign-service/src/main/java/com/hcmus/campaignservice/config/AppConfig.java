package com.hcmus.campaignservice.config;

import com.hcmus.campaignservice.dto.CampaignDto;
import com.hcmus.campaignservice.dto.CampaignResponseDto;
import com.hcmus.campaignservice.dto.UpdateCampaignDto;
import com.hcmus.campaignservice.model.Campaign;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * Configures and returns a ModelMapper bean.
     *
     * @return the configured ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configCampaignConverters(modelMapper);
        return modelMapper;
    }

    private void configCampaignConverters(ModelMapper modelMapper) {
        // DTO to Model mapping
        modelMapper.typeMap(CampaignDto.class, Campaign.class).addMappings(mapper -> {
            mapper.skip(Campaign::setId);
            mapper.skip(Campaign::setCreatedAt);
            mapper.skip(Campaign::setFavouriteCampaigns);
            mapper.map(CampaignDto::getBrandId, Campaign::setBrandId);
        });

        // Model to Response DTO mapping
        modelMapper.typeMap(Campaign.class, CampaignResponseDto.class).addMappings(mapper -> {
            mapper.map(Campaign::getName, CampaignResponseDto::setName);
            mapper.map(Campaign::getImage, CampaignResponseDto::setImage);
            mapper.map(Campaign::getField, CampaignResponseDto::setField);
            mapper.map(Campaign::getStartDate, CampaignResponseDto::setStartDate);
            mapper.map(Campaign::getEndDate, CampaignResponseDto::setEndDate);
            mapper.map(Campaign::getCreatedAt, CampaignResponseDto::setCreatedAt);
            mapper.map(Campaign::getStatus, CampaignResponseDto::setStatus);
            mapper.map(Campaign::getBrandId, CampaignResponseDto::setBrandId);
            mapper.map(Campaign::getId, CampaignResponseDto::setId);
        });

        // Update DTO to Model mapping
        modelMapper.typeMap(UpdateCampaignDto.class, Campaign.class).addMappings(mapper -> {
            mapper.skip(Campaign::setId);
            mapper.skip(Campaign::setCreatedAt);
            mapper.skip(Campaign::setFavouriteCampaigns);
            mapper.skip(Campaign::setFavouriteCampaigns);
        });
    }
}
