package com.hcmus.auth_service.controller;

import com.hcmus.auth_service.dto.AuthRequest;
import com.hcmus.auth_service.dto.AuthResponse;
import com.hcmus.auth_service.dto.UserRequestDto;
import com.hcmus.auth_service.dto.UserRespondDto;
import com.hcmus.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v3/auth")
public class AuthController {
    private final AuthService authService;
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
}
