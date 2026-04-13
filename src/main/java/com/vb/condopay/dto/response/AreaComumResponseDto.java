package com.vb.condopay.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.vb.condopay.database.entity.AreaComumModel;

public record AreaComumResponseDto(
    UUID id,
    String nome,
    String descricao,
    Integer capacidadeMaxima,
    BigDecimal valorReserva,
    Boolean diponivel,
    LocalDateTime criadoEm
) {
    public AreaComumResponseDto(AreaComumModel model) {
        this(
            model.getId(),
            model.getNome(),
            model.getDescricao(),
            model.getCapacidadeMaxima(),
            model.getValorReserva(),
            model.getDiponivel(),
            model.getCriadoEm()
        );
    }
}
