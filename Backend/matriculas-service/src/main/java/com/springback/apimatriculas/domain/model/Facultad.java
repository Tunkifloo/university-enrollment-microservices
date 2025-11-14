package com.springback.apimatriculas.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facultad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facultad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facultad_id")
    private Long facultadId;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "decano", length = 100)
    private String decano;

    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @OneToMany(mappedBy = "facultad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Carrera> carreras = new ArrayList<>();

    // Método de utilidad para agregar carrera
    public void addCarrera(Carrera carrera) {
        carreras.add(carrera);
        carrera.setFacultad(this);
    }

    // Método de utilidad para remover carrera
    public void removeCarrera(Carrera carrera) {
        carreras.remove(carrera);
        carrera.setFacultad(null);
    }
}
