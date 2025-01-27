package com.espe.micro_equipos.repositories;

import com.espe.micro_equipos.model.entity.EquipoMiembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoMiembroRepository extends JpaRepository<EquipoMiembro, Long> {
    List<EquipoMiembro> findByEquipoId(Long equipoId); // Para obtener todos los miembros de un equipo
    List<EquipoMiembro> findByMiembroId(Long miembroId); // Para obtener todos los equipos de un miembro
    Optional<EquipoMiembro> findByEquipoIdAndMiembroId(Long equipoId, Long miembroId); // Para buscar una relación específica entre equipo y miembro
}

