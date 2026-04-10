package com.vb.condopay.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.condopay.dto.request.UsuarioRequestDto;
import com.vb.condopay.dto.response.UsuarioResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class UsuarioController {
    private final com.vb.condopay.service.UsuarioService service;

    @PostMapping("/usuario")
    @PreAuthorize("hasRole('SINDICO')")
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(@RequestBody UsuarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(service.cadastrarUsuario(dto));
    }

    @GetMapping("/usuario")
    @PreAuthorize("hasRole('SINDICO')")
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuario() {
        return ResponseEntity.ok()
                             .body(service.listarUsuario());
    }

    @GetMapping("/usuario/{id}")
    @PreAuthorize("hasRole('SINDICO')")
    public ResponseEntity<UsuarioResponseDto> buscarUsuario(@PathVariable UUID id) {
        return ResponseEntity.ok()
                             .body(service.buscarUsuario(id));
    }


    @GetMapping("usuario/me")
    @PreAuthorize("hasAnyRole('SINDICO', 'MORADOR')")
    public ResponseEntity<UsuarioResponseDto> usuarioLogado(
        @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.usuarioLogado(jwt));
    }
}
