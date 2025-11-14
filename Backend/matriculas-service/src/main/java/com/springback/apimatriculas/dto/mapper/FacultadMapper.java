package com.springback.apimatriculas.dto.mapper;

import com.springback.apimatriculas.domain.model.Facultad;
import com.springback.apimatriculas.dto.request.FacultadRequestDTO;
import com.springback.apimatriculas.dto.response.FacultadResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultadMapper {

    /**
     * Convierte de Request DTO a Entidad
     */
    @Mapping(target = "facultadId", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "carreras", ignore = true)
    Facultad toEntity(FacultadRequestDTO dto);

    /**
     * Convierte de Entidad a Response DTO
     */
    @Mapping(target = "cantidadCarreras", expression = "java(contarCarrerasActivas(entity))")
    FacultadResponseDTO toResponseDTO(Facultad entity);

    /**
     * Convierte lista de entidades a lista de Response DTOs
     */
    List<FacultadResponseDTO> toResponseDTOList(List<Facultad> entities);

    /**
     * Actualiza una entidad existente con los datos del DTO
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "facultadId", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "carreras", ignore = true)
    void updateEntityFromDTO(FacultadRequestDTO dto, @MappingTarget Facultad entity);

    /**
     * Método auxiliar para contar carreras activas
     */
    default Long contarCarrerasActivas(Facultad facultad) {
        if (facultad.getCarreras() == null) {
            return 0L;
        }
        return facultad.getCarreras().stream()
                .filter(carrera -> Boolean.TRUE.equals(carrera.getActivo()))
                .count();
    }
}