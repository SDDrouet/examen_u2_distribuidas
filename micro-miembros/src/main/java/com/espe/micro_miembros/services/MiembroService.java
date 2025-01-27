package com.espe.micro_miembros.services;

import com.espe.micro_miembros.model.entity.Miembro;

import java.util.List;

public interface MiembroService {
    List<Miembro> listarTodos();

    Miembro guardarMiembro(Miembro miembro);

    Miembro actualizarMiembro(Miembro miembro);

    Miembro obtenerPorId(Long id);

    void eliminarPorId(Long id);
}
