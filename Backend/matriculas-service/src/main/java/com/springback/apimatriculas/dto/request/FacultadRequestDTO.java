package com.springback.apimatriculas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para crear o actualizar una Facultad")
public record FacultadRequestDTO(

        @Schema(description = "Nombre de la facultad", example = "Facultad de Ingeniería")
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @Schema(description = "Descripción de la facultad",
                example = "Facultad dedicada a la formación de ingenieros")
        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String descripcion,

        @Schema(description = "Ubicación de la facultad", example = "Pabellón A - Campus Principal")
        @Size(max = 100, message = "La ubicación no debe exceder los 100 caracteres")
        String ubicacion,

        @Schema(description = "Nombre del decano", example = "Dr. Juan Pérez")
        @Size(max = 100, message = "El nombre del decano no debe exceder los 100 caracteres")
        String decano,

        @Schema(description = "Estado de la facultad", example = "true", defaultValue = "true")
        Boolean activo
) {
    // Constructor compacto para valores por defecto
    public FacultadRequestDTO {
        if (activo == null) {
            activo = true;
        }
    }
}