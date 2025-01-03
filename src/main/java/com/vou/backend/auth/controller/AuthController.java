package com.vou.backend.auth.controller;

import com.vou.backend.auth.dto.LoginResponseDto;
import com.vou.backend.auth.dto.UserLoginDto;
import com.vou.backend.auth.dto.VerifyOtpDto;
import com.vou.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;
    /**
     * Logs in a user.
     *
     * @param userLoginDto the UserLoginDto containing the username and password
     * @return the LoginResponseDto containing the token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto)
    {
        try {
            String token = authService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
            LoginResponseDto loginResponse = new LoginResponseDto();
            loginResponse.setToken(token);
            return ResponseEntity.ok().body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Authenticates a user with an external provider.
     *
     * @param code the code used for authentication
     * @return the LoginResponseDto containing the token
     */
    @PostMapping("/outbound/authentication")
    ResponseEntity<?> outboundAuthenticate(@RequestParam("code") String code){
        try {
        String token = authService.outboundAuthentication(code);
        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(token);
        return ResponseEntity.ok().body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDto request) {
        try {
            boolean isValidOtp = authService.verifyOTP(request.getOtp(),request.getEmail());
            if (!isValidOtp) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is wrong");
            }
            return ResponseEntity.ok("Account active successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        try {
            return ResponseEntity.ok(authService.getMe());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
