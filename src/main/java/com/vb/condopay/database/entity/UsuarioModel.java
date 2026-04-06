package com.vb.condopay.database.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.vb.condopay.database.entity.enums.UsuarioRole;

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
@Table(name = "tb_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    private UsuarioRole role;
    private Boolean ativo = true;
    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;
    @CreatedDate
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    //Usuario -> Reserva -> OneToMany
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservaModel> reservas = new HashSet<>();

}
