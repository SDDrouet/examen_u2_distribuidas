package com.espe.micro_miembros.controllers;

import com.espe.micro_miembros.model.entity.Miembro;
import com.espe.micro_miembros.services.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/miembros")
//@CrossOrigin(origins = "http://localhost:3000")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarMiembros() {
        List<com.espe.micro_miembros.model.entity.Miembro> miembros = miembroService.listarTodos();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Miembros obtenidos exitosamente.");
        response.put("data", miembros);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerMiembro(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            com.espe.micro_miembros.model.entity.Miembro miembro = miembroService.obtenerPorId(id);
            response.put("message", "Miembro encontrado exitosamente.");
            response.put("data", miembro);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Miembro no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearMiembro(@RequestBody Miembro miembro) {
        com.espe.micro_miembros.model.entity.Miembro miembroCreado = miembroService.guardarMiembro(miembro);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Miembro creado exitosamente.");
        response.put("data", miembroCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarMiembro(@PathVariable Long id, @RequestBody Miembro miembro) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Establecer el ID del miembro que se va a actualizar
            miembro.setId(id);
            com.espe.micro_miembros.model.entity.Miembro miembroActualizado = miembroService.actualizarMiembro(miembro);
            response.put("message", "Miembro actualizado exitosamente.");
            response.put("data", miembroActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Miembro no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarMiembro(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            miembroService.eliminarPorId(id);
            response.put("message", "Miembro eliminado exitosamente.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "Miembro no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
