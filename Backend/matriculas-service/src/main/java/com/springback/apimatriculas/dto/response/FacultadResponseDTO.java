package com.springback.apimatriculas.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO de respuesta con información de una Facultad")
public record FacultadResponseDTO(

        @Schema(description = "ID de la facultad", example = "1")
        Long facultadId,

        @Schema(description = "Nombre de la facultad", example = "Facultad de Ingeniería")
        String nombre,

        @Schema(description = "Descripción de la facultad")
        String descripcion,

        @Schema(description = "Ubicación de la facultad", example = "Pabellón A")
        String ubicacion,

        @Schema(description = "Nombre del decano", example = "Dr. Juan Pérez")
        String decano,

        @Schema(description = "Fecha de registro", example = "2025-01-15T10:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime fechaRegistro,

        @Schema(description = "Estado de la facultad", example = "true")
        Boolean activo,

        @Schema(description = "Cantidad de carreras activas", example = "5")
        Long cantidadCarreras
) {
}