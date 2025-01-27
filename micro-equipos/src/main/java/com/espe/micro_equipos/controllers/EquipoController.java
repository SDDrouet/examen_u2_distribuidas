package com.espe.micro_equipos.controllers;

import com.espe.micro_equipos.model.entity.Equipo;
import com.espe.micro_equipos.services.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "http://localhost:3000") // Habilitar CORS solo para este origen
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarEquipos() {
        List<Equipo> equipos = equipoService.listarTodos();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Equipos obtenidos exitosamente.");
        response.put("data", equipos);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerEquipos(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Equipo equipo = equipoService.obtenerPorId(id);
            response.put("message", "Equipo encontrado exitosamente.");
            response.put("data", equipo);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Equipo no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearEquipo(@RequestBody Equipo equipo) {
        Equipo equipoCreado = equipoService.guardarCurso(equipo);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Equipo creado exitosamente.");
        response.put("data", equipoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        Map<String, Object> response = new HashMap<>();
        try {
            Equipo equipoExistente = equipoService.obtenerPorId(id);
            equipoExistente.setNombre(equipo.getNombre());
            equipoExistente.setDescripcion(equipo.getDescripcion());
            equipoExistente.setFrase(equipo.getFrase());
            Equipo equipoActualizado = equipoService.guardarCurso(equipoExistente);
            response.put("message", "Equipo actualizado exitosamente.");
            response.put("data", equipoActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Equipo no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarEquipo(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            equipoService.eliminarPorId(id);
            response.put("message", "Equipo eliminado exitosamente.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Equipo no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
}
