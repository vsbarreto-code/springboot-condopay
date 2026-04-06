package com.vb.condopay.database.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.condopay.database.entity.UsuarioModel;

import aj.org.objectweb.asm.commons.Remapper;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    boolean existsByEmail(String email);

    Optional<UsuarioModel> findByEmail(String email);
}
