package com.springback.apimatriculas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO para crear o actualizar una Carrera")
public record CarreraRequestDTO(

        @Schema(description = "ID de la facultad a la que pertenece", example = "1", required = true)
        @NotNull(message = "El ID de la facultad es obligatorio")
        @Positive(message = "El ID de la facultad debe ser un número positivo")
        Long facultadId,

        @Schema(description = "Nombre de la carrera", example = "Ingeniería de Sistemas")
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @Schema(description = "Descripción de la carrera",
                example = "Carrera profesional enfocada en el desarrollo de software")
        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String descripcion,

        @Schema(description = "Duración de la carrera en semestres", example = "10")
        @NotNull(message = "La duración en semestres es obligatoria")
        @Min(value = 1, message = "La duración debe ser al menos 1 semestre")
        @Max(value = 20, message = "La duración no puede exceder los 20 semestres")
        Integer duracionSemestres,

        @Schema(description = "Título que otorga la carrera", example = "Ingeniero de Sistemas")
        @Size(max = 100, message = "El título otorgado no debe exceder los 100 caracteres")
        String tituloOtorgado,

        @Schema(description = "Estado de la carrera", example = "true", defaultValue = "true")
        Boolean activo
) {
    // Constructor compacto para valores por defecto
    public CarreraRequestDTO {
        if (activo == null) {
            activo = true;
        }
    }
}
