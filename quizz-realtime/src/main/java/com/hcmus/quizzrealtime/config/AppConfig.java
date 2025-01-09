package com.hcmus.quizzrealtime.config;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    /**
     * Configures and returns a ModelMapper bean.
     *
     * @return the configured ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
