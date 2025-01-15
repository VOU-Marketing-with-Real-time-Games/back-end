package com.hcmus.campaignservice.client;
import com.hcmus.campaignservice.config.FeignClientConfig;
import com.hcmus.campaignservice.dto.UserRespondDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "http://user-service:8005/v3/users", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/{id}")
    UserRespondDto getUserByID(@PathVariable("id") Long id);

}