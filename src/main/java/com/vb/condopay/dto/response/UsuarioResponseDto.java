package com.vb.condopay.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vb.condopay.database.entity.UsuarioModel;
import com.vb.condopay.database.entity.enums.UsuarioRole;

public record UsuarioResponseDto(
    UUID id,
    String nome,
    String email,
    UsuarioRole role,
    Boolean ativo,
    LocalDateTime criadoEm
) {
    public UsuarioResponseDto(UsuarioModel model) {
        this(
            model.getId(),
            model.getNome(),
            model.getEmail(),
            model.getRole(),
            model.getAtivo(),
            model.getCriadoEm()
        );
    }
}
