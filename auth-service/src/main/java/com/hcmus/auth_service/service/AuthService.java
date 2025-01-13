package com.hcmus.auth_service.service;
import com.hcmus.auth_service.client.OutboundIdentityClient;
import com.hcmus.auth_service.client.OutboundUserClient;
import com.hcmus.auth_service.client.UserClient;
import com.hcmus.auth_service.dto.*;
import com.hcmus.auth_service.exception.InvalidRefreshTokenException;
import com.hcmus.auth_service.exception.NotActiveAccountException;
import com.hcmus.auth_service.jwt.JwtUtils;
import com.hcmus.auth_service.model.RefreshToken;
import com.hcmus.auth_service.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final UserClient userClient;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OutboundIdentityClient outboundIdentityClient;
    private final OutboundUserClient outboundUserClient;
    private final EmailService emailService;
    private final OtpService otpService;
    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;
    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;
    @NonFinal
    @Value("${redirect-uri}")
    protected String REDIRECT_URI;
    @NonFinal
    protected String GRANT_TYPE = "authorization_code";
    public UserRespondDto register(UserRequestDto userDto) {
            return userClient.createUser(userDto);
    }
    public AuthResponse login(AuthRequest authRequest) throws NotActiveAccountException {
        UserRespondDto userRespondDto = userClient.validateUser(authRequest);
        if(userRespondDto.getStatus().equals("INACTIVE"))
        {
            throw new NotActiveAccountException("Account is not active");
        }
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

    public AuthResponse outboundAuthentication(String code) throws Exception {
        // Exchange token using the identity client
        var tokenResponse = outboundIdentityClient.exchangeToken(
                ExchangeTokenRequest.builder()
                        .code(code)
                        .clientId(CLIENT_ID)
                        .clientSecret(CLIENT_SECRET)
                        .redirectUri(REDIRECT_URI)
                        .grantType(GRANT_TYPE)
                        .build()
        );

        // Fetch user info using the outbound user client
        OutboundUserResponse userResponse = outboundUserClient.getUserInfo("json", tokenResponse.getAccessToken());
        String email = userResponse.getEmail();
        String imageUrl = userResponse.getPicture();
        // Check if the user already exists
        UserRespondDto userRespondDto = userClient.getUserByEmail(email);
        if(userRespondDto == null)
        {
            userRespondDto = userClient.createUser(UserRequestDto.builder()
                    .email(email)
                    .username(userResponse.getName())
                    .password("123456")
                    .avatar(userResponse.getPicture())
                    .build());
        }
        String jwt = jwtUtils.generateJwtToken(userRespondDto);
        UserRespondDto finalUserRespondDto = userRespondDto;
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userRespondDto.getId())
                .filter(token -> token.getExpiryDate().isAfter(Instant.now()))
                .orElseGet(() -> generateRefreshToken(finalUserRespondDto));
        // Return the response DTO
        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .build();
    }
    public void sendOTP(String email)
    {
        UserRespondDto userRespondDto = userClient.getUserByEmail(email);
        String otp = emailService.generateOtp();
        otpService.storeOtp(email,otp);
        emailService.sendOtpEmail(email, otp);
    }
    public boolean verifyOTP(String otp,String email) throws Exception
    {
        UserRespondDto userRespondDto = userClient.getUserByEmail(email);
        boolean valid = otpService.verifyOtp(otp,email);
        //end point active user here
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .avatar(userRespondDto.getAvatar())
                .email(userRespondDto.getEmail())
                .fullName(userRespondDto.getFullName())
                .role(userRespondDto.getRole())
                .status("ACTIVE")
                .build();
        userClient.updateUser(userRespondDto.getId(),userUpdateDto);
        return valid;
    }
    public RefreshToken getRefreshTokenFromEmail(String email) {
        UserRespondDto userRespondDto = userClient.getUserByEmail(email);
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userRespondDto.getId())
                .filter(token -> token.getExpiryDate().isAfter(Instant.now()))
                .orElseGet(() -> generateRefreshToken(userRespondDto));
        return refreshToken;
    }
    public void changePassword(ResetPasswordDto resetPasswordDto) throws InvalidRefreshTokenException {
        RefreshToken token = refreshTokenRepository.findByToken(resetPasswordDto.getRefreshToken())
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));
        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Refresh token has expired");
        }
        UserRespondDto userRespondDto = userClient.getUserByID(token.getUserId());
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .password(resetPasswordDto.getPassword())
                .avatar(userRespondDto.getAvatar())
                .email(userRespondDto.getEmail())
                .fullName(userRespondDto.getFullName())
                .role(userRespondDto.getRole())
                .status(userRespondDto.getStatus())
                .build();
        userClient.updateUser(userRespondDto.getId(),userUpdateDto);
    }
    public String getNewAccessToken(String refreshToken) throws InvalidRefreshTokenException {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));
        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Refresh token has expired");
        }
        UserRespondDto userRespondDto = userClient.getUserByID(token.getUserId());
        return jwtUtils.generateRefreshToken(userRespondDto.getUsername());
    }
}
