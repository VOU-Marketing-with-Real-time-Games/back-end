package com.hcmus.auth_service.controller;

import com.hcmus.auth_service.dto.*;
import com.hcmus.auth_service.exception.InvalidRefreshTokenException;
import com.hcmus.auth_service.jwt.JwtUtils;
import com.hcmus.auth_service.model.RefreshToken;
import com.hcmus.auth_service.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v3/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) throws Exception{
        UserRespondDto userRespondDto = authService.register(userRequestDto);
        return ResponseEntity.ok(userRespondDto);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception{
        AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.ok(authResponse);
    }
    /**
     * Sends an OTP to an email.
     *
     * @param email the email to send the OTP to
     * @return a ResponseEntity containing the result of the operation
     */
    @GetMapping("/get-otp")
    public ResponseEntity<?> getOtp(@RequestParam String email) throws Exception {
        authService.sendOTP(email);
        return ResponseEntity.ok("OTP sent successfully");
    }
    /**
     * Verifies an OTP.
     *
     * @param request the VerifyOtpDto containing the OTP and email
     * @return a ResponseEntity containing the result of the verification
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDto request) throws Exception {
        boolean isValidOtp = authService.verifyOTP(request.getOtp(),request.getEmail());
        if (!isValidOtp) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is wrong");
        }
        return ResponseEntity.ok("Account active successfully");
    }
    /**
     * Refreshes an access token.
     *
     * @param newAccessTokenDto the NewAccessTokenDto containing the refresh token
     * @return a ResponseEntity containing the new access token
     */
    @PostMapping("refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody NewAccessTokenDto newAccessTokenDto) throws InvalidRefreshTokenException {
        String refreshToken = newAccessTokenDto.getRefreshToken();
        String newAccessToken = authService.getNewAccessToken(refreshToken);
        return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
    }
    /**
     * Verifies an OTP and changes the password.
     *
     * @param request the VerifyOtpDto containing the OTP and email
     * @return a ResponseEntity containing the result of the verification
     */
    @PostMapping("/verify-password")
    public ResponseEntity<?> verifyOtpChangePassword(@RequestBody VerifyOtpDto request) throws Exception {
        boolean isValidOtp = authService.verifyOTP(request.getOtp(),request.getEmail());
        if (!isValidOtp) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is wrong");
        }
        RefreshToken refreshToken = authService.getRefreshTokenFromEmail(request.getEmail());
        return ResponseEntity.ok(refreshToken);
    }
    /**
     * Handles POST requests for Google login authentication.
     *
     * @param code the authorization code from Google's OAuth 2.0 flow.
     * @return a ResponseEntity containing the authentication result (e.g., user info or token).
     * @throws Exception if authentication fails or communication with Google encounters an issue.
     *
     * This method exchanges the provided code for access and ID tokens via the
     * `authenticationService`, validates the user's identity, and returns the result.
     */
    @PostMapping("/outbound/authentication")
    ResponseEntity<?> outboundAuthenticate(@RequestParam("code") String code) throws Exception {
        var result = authService.outboundAuthentication(code);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Handles POST requests to reset the user's password.
     *
     * @param resetPasswordDto the DTO containing the necessary information to reset the password
     * @return a ResponseEntity indicating the success of the password change operation
     */
    @PostMapping("/reset-password")
    ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws InvalidRefreshTokenException {
        authService.changePassword(resetPasswordDto);
        return ResponseEntity.ok("Password changed successfully");
    }

    /**
     * Get user profile
     * @param request
     * @return
     */
    @GetMapping("/me")
    ResponseEntity<?> getProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            Long userId = jwtUtils.getIdFromJwtToken(authHeader.substring(7));
            return new ResponseEntity<>(authService.getUserById(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
