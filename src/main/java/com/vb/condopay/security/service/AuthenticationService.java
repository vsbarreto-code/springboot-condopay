package com.vb.condopay.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
