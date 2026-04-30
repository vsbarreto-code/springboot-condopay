package com.vb.condopay.database.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.condopay.database.entity.ReservaModel;
import com.vb.condopay.database.entity.enums.StatusEnum;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public interface IReservaRepository extends JpaRepository<ReservaModel, UUID> {
    Optional<ReservaModel> findByUsuarioId(UUID id);


    boolean existsByAreaComumIdAndStatusAndDataInicioLessThanAndDataFimGreaterThan(
        @NotNull(message = "Deve ser informado o id da área comum") UUID uuid,
        StatusEnum statusEnum,
        @NotNull(message = "Deve ser informado a data de fim") @FutureOrPresent(message = "A data deve ser de hoje ou posterior") LocalDateTime localDateTime,
        @NotNull(message = "Deve ser informado a data de inicio") @FutureOrPresent(message = "A data de inicio deve ser hoje ou posterior a hoje") LocalDateTime localDateTime1);
}
