package com.springback.apimatriculas.dto.mapper;

import com.springback.apimatriculas.domain.model.Carrera;
import com.springback.apimatriculas.domain.model.Facultad;
import com.springback.apimatriculas.dto.request.CarreraRequestDTO;
import com.springback.apimatriculas.dto.response.CarreraResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarreraMapper {

    /**
     * Convierte de Request DTO a Entidad
     */
    @Mapping(target = "carreraId", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "facultad", source = "facultadId", qualifiedByName = "facultadIdToFacultad")
    Carrera toEntity(CarreraRequestDTO dto);

    /**
     * Convierte de Entidad a Response DTO
     */
    @Mapping(target = "facultadId", source = "facultad.facultadId")
    @Mapping(target = "nombreFacultad", source = "facultad.nombre")
    CarreraResponseDTO toResponseDTO(Carrera entity);

    /**
     * Convierte lista de entidades a lista de Response DTOs
     */
    List<CarreraResponseDTO> toResponseDTOList(List<Carrera> entities);

    /**
     * Actualiza una entidad existente con los datos del DTO
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "carreraId", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "facultad", source = "facultadId", qualifiedByName = "facultadIdToFacultad")
    void updateEntityFromDTO(CarreraRequestDTO dto, @MappingTarget Carrera entity);

    /**
     * Mapea el ID de facultad a una entidad Facultad
     */
    @Named("facultadIdToFacultad")
    default Facultad facultadIdToFacultad(Long facultadId) {
        if (facultadId == null) {
            return null;
        }
        Facultad facultad = new Facultad();
        facultad.setFacultadId(facultadId);
        return facultad;
    }
}
