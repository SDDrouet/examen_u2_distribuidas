package com.espe.micro_miembros.services;

import com.espe.micro_miembros.model.entity.Miembro;
import com.espe.micro_miembros.repositories.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiembroServiceImpl implements MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Override
    public List<Miembro> listarTodos() {
        return miembroRepository.findAll();
    }

    @Override
    public Miembro guardarMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    @Override
    public Miembro actualizarMiembro(Miembro miembro) {
        // Verifica si el miembro existe
        return miembroRepository.findById(miembro.getId()).map(existingMiembro -> {
            // Actualiza los campos
            existingMiembro.setNombre(miembro.getNombre());
            existingMiembro.setApellido(miembro.getApellido());
            existingMiembro.setEmail(miembro.getEmail());
            existingMiembro.setTelefono(miembro.getTelefono());
            // Guarda los cambios en la base de datos
            return miembroRepository.save(existingMiembro);
        }).orElseThrow(() -> new RuntimeException("Miembro no encontrado con ID: " + miembro.getId()));
    }


    @Override
    public Miembro obtenerPorId(Long id) {
        return miembroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado con ID: " + id));
    }

    @Override
    public void eliminarPorId(Long id) {
        if (!miembroRepository.existsById(id)) {
            throw new RuntimeException("Miembro no encontrado con ID: " + id);
        }
        miembroRepository.deleteById(id);
    }
}
