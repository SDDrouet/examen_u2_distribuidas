package com.espe.micro_equipos.controllers;

import com.espe.micro_equipos.model.entity.Equipo;
import com.espe.micro_equipos.model.entity.Miembro;
import com.espe.micro_equipos.services.EquipoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipo-miembro")
public class EquipoMiembroController {

    @Autowired
    EquipoService equipoService;

    @PostMapping("/{equipoId}")
    public ResponseEntity<?> asignarMiembro(@RequestBody Miembro miembro, @PathVariable Long equipoId) {
        Optional<Miembro> o;
        try {
            o = equipoService.addEstudiante(miembro, equipoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("mensaje", "No existe el miembro por el id: " + miembro.getId() + ", Error: " + e.getMessage()));
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{equipoId}/miembros")
    public ResponseEntity<?> listarMiembrosDeEquipo(@PathVariable Long equipoId) {
        List<Miembro> miembros = equipoService.listarMiembrosDeEquipo(equipoId);
        return ResponseEntity.ok(miembros);
    }

    @GetMapping("/miembro/{miembroId}/equipos")
    public ResponseEntity<?> listarEquiposDeMiembro(@PathVariable Long miembroId) {
        List<Equipo> equipos = equipoService.listarEquiposDeMiembro(miembroId);
        return ResponseEntity.ok(equipos);
    }

    @DeleteMapping("/{equipoId}/miembro/{miembroId}")
    public ResponseEntity<?> eliminarMiembroDeEquipo(@PathVariable Long equipoId, @PathVariable Long miembroId) {
        equipoService.eliminarMiembroDeEquipo(equipoId, miembroId);
        return ResponseEntity.noContent().build();
    }
}

