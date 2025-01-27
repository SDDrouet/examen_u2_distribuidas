package com.espe.micro_equipos.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="equipo_miembro",
        uniqueConstraints = @UniqueConstraint(columnNames = {"estudiante_id", "curso_id"}))
public class EquipoMiembro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "miembro_id", nullable = false)
    private Long miembroId;

    @Column(name = "equipo_id", nullable = false)
    private Long equipoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(Long miembroId) {
        this.miembroId = miembroId;
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }
}