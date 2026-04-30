package com.vb.condopay.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.vb.condopay.database.entity.ReservaModel;
import com.vb.condopay.database.entity.enums.StatusEnum;

public record ReservaResponseDto(
    UUID id,
    StatusEnum status,
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    LocalDateTime criadoEm,
    BigDecimal valorCobrado

) {
    public ReservaResponseDto(ReservaModel model) {
        this(model.getId(),
             model.getStatus(),
             model.getDataInicio(),
             model.getDataFim(),
             model.getCriadoEm(),
             model.getValorCobrado());
    }
}
