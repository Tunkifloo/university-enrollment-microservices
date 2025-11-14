package com.springback.apimatriculas.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO de respuesta con información de una Carrera")
public record CarreraResponseDTO(

        @Schema(description = "ID de la carrera", example = "1")
        Long carreraId,

        @Schema(description = "ID de la facultad", example = "1")
        Long facultadId,

        @Schema(description = "Nombre de la facultad", example = "Facultad de Ingeniería")
        String nombreFacultad,

        @Schema(description = "Nombre de la carrera", example = "Ingeniería de Sistemas")
        String nombre,

        @Schema(description = "Descripción de la carrera")
        String descripcion,

        @Schema(description = "Duración en semestres", example = "10")
        Integer duracionSemestres,

        @Schema(description = "Título otorgado", example = "Ingeniero de Sistemas")
        String tituloOtorgado,

        @Schema(description = "Fecha de registro", example = "2025-01-15T10:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime fechaRegistro,

        @Schema(description = "Estado de la carrera", example = "true")
        Boolean activo
) {
}