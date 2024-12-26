package com.vou.backend.auth.controller;

import com.vou.backend.auth.dto.LoginResponseDto;
import com.vou.backend.auth.dto.UserLoginDto;
import com.vou.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService userService;
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
            String token = userService.login(userLoginDto.getUserName(), userLoginDto.getPassword());
            LoginResponseDto loginResponse = new LoginResponseDto();
            loginResponse.setToken(token);
            return ResponseEntity.ok().body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
