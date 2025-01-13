package com.hcmus.auth_service.service;
import com.hcmus.auth_service.client.UserClient;
import com.hcmus.auth_service.dto.AuthRequest;
import com.hcmus.auth_service.dto.AuthResponse;
import com.hcmus.auth_service.dto.UserRequestDto;
import com.hcmus.auth_service.dto.UserRespondDto;
import com.hcmus.auth_service.exception.CustomFeignException;
import com.hcmus.auth_service.jwt.JwtUtils;
import com.hcmus.auth_service.model.RefreshToken;
import com.hcmus.auth_service.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final UserClient userClient;
    private final RefreshTokenRepository refreshTokenRepository;
    public UserRespondDto register(UserRequestDto userDto) {
            return userClient.createUser(userDto);
    }
    public AuthResponse login(AuthRequest authRequest) {
        UserRespondDto userRespondDto = userClient.validateUser(authRequest);
        String accessToken = jwtUtils.generateJwtToken(userRespondDto);
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userRespondDto.getId())
                .filter(token -> token.getExpiryDate().isAfter(Instant.now()))
                .orElseGet(() -> generateRefreshToken(userRespondDto));
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }
    public RefreshToken generateRefreshToken(UserRespondDto user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getId());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate();
        return refreshTokenRepository.save(refreshToken);
    }

}
