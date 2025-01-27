package com.espe.micro_equipos.clients;

import com.espe.micro_equipos.model.entity.Miembro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient(name="micro-miembros", url = "localhost:8002/api/miembros")
public interface MiembroClient {
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarMiembros();

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerMiembro(@PathVariable Long id);

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearMiembro(@RequestBody Miembro miembro);

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarMiembro(@PathVariable Long id, @RequestBody Miembro miembro);

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarMiembro(@PathVariable Long id);
}
