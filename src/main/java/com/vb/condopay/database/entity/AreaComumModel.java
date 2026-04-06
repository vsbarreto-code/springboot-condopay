package com.vb.condopay.database.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class AreaComumModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String descricao;
    @Column(name = "stripe_price_id")
    private String stripePriceId;

    //Area_Comum -> Reserva -> OneToMany
    @OneToMany(mappedBy = "areaComum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservaModel> reservas = new HashSet<>();

}
