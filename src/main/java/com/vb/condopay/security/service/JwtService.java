package com.vb.condopay.security.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder encoder;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now(); // VAI INICIAR AGORA
        long expiry = 3600L; // VAI EXPIRAR

        //Pegando os papeis/roles
        String scopes = authentication.getAuthorities()
                                      .stream()
                                      .map(GrantedAuthority::getAuthority)
                                      .collect(Collectors.joining(" "));

        //Pegando as propriedades/claim
        var claims = JwtClaimsSet.builder()
                                 .issuer("spring-security-jwt")
                                 .issuedAt(now)
                                 .expiresAt(now.plusSeconds(expiry))
                                 .subject(authentication.getName())
                                 .claim("scope", scopes)
                                 .build();

        //Retornar ele codificado
        return encoder.encode(JwtEncoderParameters.from(claims))
                      .getTokenValue();
    }
}
