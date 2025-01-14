package com.hcmus.auth_service.client;
import com.hcmus.auth_service.config.FeignClientConfig;
import com.hcmus.auth_service.dto.AuthRequest;
import com.hcmus.auth_service.dto.UserRequestDto;
import com.hcmus.auth_service.dto.UserRespondDto;
import com.hcmus.auth_service.dto.UserUpdateDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "http://user-service:8005/v3/users", configuration = FeignClientConfig.class)
public interface UserClient {
    @PostMapping()
    UserRespondDto createUser(@RequestBody UserRequestDto userDto);
    @PostMapping("/validate")
    UserRespondDto validateUser(@RequestBody AuthRequest authRequest);
    @GetMapping("/email/{email}")
    UserRespondDto getUserByEmail(@PathVariable("email") String email);
    @GetMapping("/{id}")
    UserRespondDto getUserByID(@PathVariable("id") Long id);
    @PutMapping("/{id}")
    UserRespondDto updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDto userDto);
}