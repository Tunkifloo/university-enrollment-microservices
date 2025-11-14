package com.springback.apimatriculas.repository;

import com.springback.apimatriculas.domain.model.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long> {

    Optional<Facultad> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Facultad> findByActivoTrue();
    List<Facultad> findByActivoFalse();

    @Query("SELECT f FROM Facultad f WHERE LOWER(f.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Facultad> findByNombreContaining(String nombre);
    @Query("SELECT COUNT(c) FROM Carrera c WHERE c.facultad.facultadId = :facultadId AND c.activo = true")
    Long countCarrerasActivasByFacultadId(Long facultadId);
}