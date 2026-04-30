package com.vb.condopay.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.condopay.dto.request.ReservaRequestDto;
import com.vb.condopay.dto.response.ReservaResponseDto;
import com.vb.condopay.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;

    // POST -> Criar reserva
    @PostMapping("/reserva")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<ReservaResponseDto> criarReserva(
        @RequestBody ReservaRequestDto dto,
        @AuthenticationPrincipal Jwt jwt) {

        var response = service.criarReserva(dto, jwt);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(response);
    }

    // GET -> Listar reservas do usuário autenticado
    @GetMapping("reserva")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<List<ReservaResponseDto>> listarReservas(
        @AuthenticationPrincipal Jwt jwt) {

        var lista = service.listarReservaAutenticado(jwt);

        return ResponseEntity.ok(lista);
    }

    // GET -> Buscar por ID
    @GetMapping("/reserva/{id}")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<ReservaResponseDto> detalharReserva(@PathVariable UUID id) {
        var reserva = service.detalharReserva(id);
        return ResponseEntity.ok(reserva);
    }


    // PATCH -> Cancelar reserva
    @PatchMapping("reserva/{id}/cancelar")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<Void> cancelarReserva(@PathVariable UUID id) {
        service.cancelarReserva(id);
        return ResponseEntity.noContent()
                             .build();
    }
}