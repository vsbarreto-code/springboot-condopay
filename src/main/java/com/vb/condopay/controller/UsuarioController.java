package com.vb.condopay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.condopay.UsuarioService;
import com.vb.condopay.dto.request.UsuarioRequestDto;
import com.vb.condopay.dto.response.UsuarioResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping("/usuario")
    @PreAuthorize("hasRole('SINDICO')")
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(@RequestBody UsuarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(service.cadastrarUsuario(dto));
    }
}
