package com.vb.condopay.database.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.condopay.database.entity.AreaComumModel;

public interface IAreaComumRepository extends JpaRepository<AreaComumModel, UUID> {
}
