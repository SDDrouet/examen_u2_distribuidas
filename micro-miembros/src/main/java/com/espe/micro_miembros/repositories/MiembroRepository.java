package com.espe.micro_miembros.repositories;

import com.espe.micro_miembros.model.entity.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {
}
