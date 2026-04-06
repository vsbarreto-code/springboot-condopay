package com.vb.condopay.database.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.condopay.database.entity.UsuarioModel;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    boolean existsByEmail(String email);
}
