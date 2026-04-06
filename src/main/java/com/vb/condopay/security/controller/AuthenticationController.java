package com.vb.condopay.security.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.vb.condopay.dto.request.LoginRequestDto;
import com.vb.condopay.security.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginRequestDto dto) {

        var authenticationToken =
            new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        return jwtService.generateToken(authenticated);
    }
}

