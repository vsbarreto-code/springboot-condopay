package com.vb.condopay.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.condopay.dto.request.AreaComumRequestDto;
import com.vb.condopay.dto.response.AreaComumResponseDto;
import com.vb.condopay.service.AreaComumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class AreaComumController {

    private final AreaComumService service;

    @PostMapping("/areas")
    @PreAuthorize("hasRole('SINDICO')")
    public ResponseEntity<AreaComumResponseDto> cadastrarAreaComum(@RequestBody AreaComumRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(service.cadastrarAreaComum(dto));
    }

    @GetMapping("/areas")
    @PreAuthorize("hasAnyRole('SINDICO', 'MORADOR')")
    public ResponseEntity<List<AreaComumResponseDto>> listarAreaComum() {
        return ResponseEntity.ok()
                             .body(service.listarAreaComum());
    }

    @GetMapping("/areas/{id}")
    @PreAuthorize("hasAnyRole('SINDICO', 'MORADOR')")
    public ResponseEntity<AreaComumResponseDto> buscarAreaComum(@PathVariable UUID id) {
        return ResponseEntity.ok()
                             .body(service.buscarAreaComum(id));
    }

    @DeleteMapping("/areas/{id}")
    @PreAuthorize("hasRole('SINDICO')")
    public ResponseEntity<String> deletarAreaComum(@PathVariable UUID id) {
        service.deletarAreaComum(id);
        return ResponseEntity.noContent()
                             .build();
    }
}

