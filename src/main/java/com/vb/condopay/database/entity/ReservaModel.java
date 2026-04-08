package com.vb.condopay.database.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.vb.condopay.database.entity.enums.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReservaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private StatusEnum status = StatusEnum.CONFIRMADO;
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;
    @Column(name = "data_fim")
    private LocalDateTime dataFim;
    @CreatedDate
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @Column(name = "valor_cobrado")
    private BigDecimal valorCobrado;

    //Reserva -> Usuario -> ManyToOne
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    //Reserva -> Area_Comum -> ManyToOne
    @ManyToOne
    @JoinColumn(name = "area_comum_id")
    private AreaComumModel areaComum;
}
