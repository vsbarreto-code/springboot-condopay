package com.vb.condopay.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AreaComumRequestDto(
    @NotBlank(message = "O nome deve ser informado")
    String nome,
    @NotBlank(message = "A descricao deve ser informada")
    String descricao,
    @NotNull(message = "A capacidade deve ser informada")
    Integer capacidadeMaxima,
    @NotNull(message = "O valor dever ser informado")
    @Positive(message = "O valor deve ser positivo")
    @Digits(integer = 6, fraction = 2, message = "O preço deve ter no máximo 6 dígitos na parte inteira e 2 na parte decimal.")
    BigDecimal valorReserva
) {
}
