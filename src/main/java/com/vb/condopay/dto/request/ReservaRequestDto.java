package com.vb.condopay.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record ReservaRequestDto(
    @NotNull(message = "Deve ser informado a data de inicio")
    @FutureOrPresent(message = "A data de inicio deve ser hoje ou posterior a hoje")
    LocalDateTime dataInicio,
    @NotNull(message = "Deve ser informado a data de fim")
    @FutureOrPresent(message = "A data deve ser de hoje ou posterior")
    LocalDateTime dataFim,
    @NotNull(message = "Deve ser informado o id da área comum")
    UUID areaComumId
) {
}
