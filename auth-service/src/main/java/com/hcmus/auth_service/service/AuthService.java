package com.hcmus.auth_service.service;
import com.hcmus.auth_service.client.UserClient;
import com.hcmus.auth_service.dto.UserRequestDto;
import com.hcmus.auth_service.dto.UserRespondDto;
import com.hcmus.auth_service.exception.CustomFeignException;
import com.hcmus.auth_service.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final UserClient userClient;
    public UserRespondDto register(UserRequestDto userDto) {
            return userClient.createUser(userDto);
    }
}
