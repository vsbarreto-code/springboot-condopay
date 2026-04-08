package com.vb.condopay.dto.request;

import com.vb.condopay.database.entity.enums.UsuarioRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDto(
    @NotBlank(message = "O nome do usuário é obrigatório")
    String nome,
    @Email(message = "O formato do email é invalido")
    String email,
    @NotBlank(message = "A senha do usuário é obrigatório")
    String senha,
    @NotNull(message = "O tipo do usuário é obrigatório")
    UsuarioRole role
) {
}
