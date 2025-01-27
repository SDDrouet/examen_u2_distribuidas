package com.espe.micro_equipos.services;

import com.espe.micro_equipos.model.entity.Equipo;
import com.espe.micro_equipos.model.entity.Miembro;

import java.util.List;
import java.util.Optional;

public interface EquipoService {
    List<Equipo> listarTodos();

    Equipo guardarCurso(Equipo equipo);

    Equipo obtenerPorId(Long id);

    void eliminarPorId(Long id);

    Equipo actualizarEquipo(Equipo equipo);

    Optional<Miembro> addEstudiante(Miembro estudiante, Long id);

    public List<Miembro> listarMiembrosDeEquipo(Long equipoId);

    public List<Equipo> listarEquiposDeMiembro(Long miembroId);

    public void eliminarMiembroDeEquipo(Long equipoId, Long miembroId);

}
