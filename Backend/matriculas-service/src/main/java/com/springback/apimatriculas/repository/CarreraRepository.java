package com.springback.apimatriculas.repository;

import com.springback.apimatriculas.domain.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    Optional<Carrera> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Carrera> findByActivoTrue();
    List<Carrera> findByActivoFalse();
    List<Carrera> findByFacultad_FacultadId(Long facultadId);

    @Query("SELECT c FROM Carrera c WHERE c.facultad.facultadId = :facultadId AND c.activo = true")
    List<Carrera> findActivasByFacultadId(Long facultadId);
    @Query("SELECT c FROM Carrera c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Carrera> findByNombreContaining(String nombre);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Carrera c " +
            "WHERE c.nombre = :nombre AND c.carreraId <> :carreraId")
    boolean existsByNombreAndCarreraIdNot(String nombre, Long carreraId);
}