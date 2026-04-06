package com.vb.condopay.dto.request;

import com.vb.condopay.database.entity.enums.UsuarioRole;

public record UsuarioRequestDto(
    String nome,
    String email,
    String senha,
    UsuarioRole role,
    String stripeCustomerId //QUANDO ENTRAR API STRIPE SAI!!!
) {
}
