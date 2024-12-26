package com.vou.backend.auth.service;

import com.vou.backend.user.exception.UserNotFoundException;
import com.vou.backend.user.model.User;
import com.vou.backend.user.repository.UserRepository;
import com.vou.backend.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    public String login(String userName, String password) throws Exception {
        User user = userRepository.findByUsername(userName);
        if(user==null)
        {
            throw new UserNotFoundException("Invalid username!");
        }
        if(!passwordEncoder.matches(password,user.getPassword()))
        {
            throw new BadCredentialsException("Invalid password!");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generateToken(user);
    }
}
