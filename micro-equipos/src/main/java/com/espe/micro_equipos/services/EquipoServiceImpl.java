package com.espe.micro_equipos.services;

import com.espe.micro_equipos.clients.MiembroClient;
import com.espe.micro_equipos.model.entity.Equipo;
import com.espe.micro_equipos.model.entity.EquipoMiembro;
import com.espe.micro_equipos.model.entity.Miembro;
import com.espe.micro_equipos.repositories.EquipoMiembroRepository;
import com.espe.micro_equipos.repositories.EquipoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private EquipoMiembroRepository equipoMiembroRepository;

    @Autowired
    private MiembroClient estudianteClient;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<Equipo> listarTodos() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo guardarCurso(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo obtenerPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }

    @Override
    public void eliminarPorId(Long id) {
        equipoRepository.deleteById(id);
    }

    @Override
    public Equipo actualizarEquipo(Equipo equipo) {
        // Verifica si el equipo existe
        return equipoRepository.findById(equipo.getId()).map(existingEquipo -> {
            // Actualiza los campos
            existingEquipo.setNombre(equipo.getNombre());
            existingEquipo.setDescripcion(equipo.getDescripcion());
            existingEquipo.setFrase(equipo.getFrase());
            // Guarda los cambios en la base de datos
            return equipoRepository.save(existingEquipo);
        }).orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + equipo.getId()));
    }


    @Override
    public Optional<Miembro> addEstudiante(Miembro miembro, Long id) {
        Optional<Equipo> optionalCurso = equipoRepository.findById(id);
        if (optionalCurso.isPresent()) {
            // Obtener el miembro del servicio externo
            ResponseEntity<Map<String, Object>> response = estudianteClient.obtenerMiembro(miembro.getId());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Convertir la respuesta del cuerpo a Estudiante
                try {
                    Miembro miembroAux = objectMapper.convertValue(response.getBody().get("data"), Miembro.class);

                    Equipo equipo = optionalCurso.get();
                    EquipoMiembro equipoMiembro = new EquipoMiembro();

                    equipoMiembro.setMiembroId(miembroAux.getId());
                    equipoMiembro.setEquipoId(equipo.getId());

                    equipoMiembroRepository.save(equipoMiembro);

                    return Optional.of(miembroAux);
                } catch (Exception e) {
                    // Manejo de errores en caso de fallos de conversión
                    e.printStackTrace();
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Miembro> listarMiembrosDeEquipo(Long equipoId) {
        return equipoMiembroRepository.findByEquipoId(equipoId).stream()
                .map(equipoMiembro -> {
                    ResponseEntity<Map<String, Object>> response = estudianteClient.obtenerMiembro(equipoMiembro.getMiembroId());
                    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                        return objectMapper.convertValue(response.getBody().get("data"), Miembro.class);
                    }
                    return null;
                })
                .filter(miembro -> miembro != null)
                .toList();
    }

    @Override
    public List<Equipo> listarEquiposDeMiembro(Long miembroId) {
        return equipoMiembroRepository.findByMiembroId(miembroId).stream()
                .map(equipoMiembro -> equipoRepository.findById(equipoMiembro.getEquipoId()).orElse(null))
                .filter(equipo -> equipo != null)
                .toList();
    }

    @Override
    public void eliminarMiembroDeEquipo(Long equipoId, Long miembroId) {
        equipoMiembroRepository.findByEquipoIdAndMiembroId(equipoId, miembroId).ifPresent(equipoMiembroRepository::delete);
    }



}
