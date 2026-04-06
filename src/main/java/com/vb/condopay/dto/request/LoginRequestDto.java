package com.vb.condopay.dto.request;

import jakarta.validation.constraints.Email;

public record LoginRequestDto(
    @Email(message = "O formato do email é invalido")
    String email,
    String senha
) {
}
