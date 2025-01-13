package com.hcmus.auth_service.client;
import com.hcmus.auth_service.config.FeignClientConfig;
import com.hcmus.auth_service.dto.UserRequestDto;
import com.hcmus.auth_service.dto.UserRespondDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8005/v3/users", configuration = FeignClientConfig.class)
public interface UserClient {
    @PostMapping()
    UserRespondDto createUser(@RequestBody UserRequestDto userDto);
}