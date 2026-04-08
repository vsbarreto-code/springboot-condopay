package com.vb.condopay.database.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_areas_comuns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AreaComumModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String descricao;
    @Column(name = "capacidade_maxima")
    private Integer capacidadeMaxima;
    @Column(name = "valor_reserva")
    private BigDecimal valorReserva;
    private Boolean diponivel = true;
    @CreatedDate
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    //Area_Comum -> Reserva -> OneToMany
    @OneToMany(mappedBy = "areaComum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservaModel> reservas = new HashSet<>();

}
