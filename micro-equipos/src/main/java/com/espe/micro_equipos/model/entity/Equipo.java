package com.espe.micro_equipos.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String frase;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creadoEn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "equipo_id")
    private List<EquipoMiembro> equipoMiembros;

    public Equipo() {
        equipoMiembros = new ArrayList<>();
    }

    public void addEquipoMiembro(EquipoMiembro equipoMiembro) {
        equipoMiembros.add(equipoMiembro);
    }

    public void removeEquipoMiembro(EquipoMiembro equipoMiembro) {
        equipoMiembros.remove(equipoMiembro);
    }

    @PrePersist
    protected void prePersist() {
        if (this.creadoEn == null) {
            this.creadoEn = new Date(); // Establece la fecha actual
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<EquipoMiembro> getEquipoMiembro() {
        return equipoMiembros;
    }

    public void setEquipoMiembro(List<EquipoMiembro> equipoMiembros) {
        this.equipoMiembros = equipoMiembros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public Date getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Date creadoEn) {
        this.creadoEn = creadoEn;
    }
}
