package com.springback.apimatriculas.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Estructura de respuesta de error")
public record ErrorResponse(

        @Schema(description = "Código de estado HTTP", example = "404")
        int status,

        @Schema(description = "Mensaje de error", example = "Recurso no encontrado")
        String message,

        @Schema(description = "Detalles adicionales del error")
        String details,

        @Schema(description = "Fecha y hora del error")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,

        @Schema(description = "Ruta de la petición", example = "/api/v1/facultades/999")
        String path,

        @Schema(description = "Lista de errores de validación")
        List<ValidationError> validationErrors
) {
    public ErrorResponse(int status, String message, String details, String path) {
        this(status, message, details, LocalDateTime.now(), path, null);
    }

    public ErrorResponse(int status, String message, String details, String path, List<ValidationError> validationErrors) {
        this(status, message, details, LocalDateTime.now(), path, validationErrors);
    }

    @Schema(description = "Error de validación individual")
    public record ValidationError(
            @Schema(description = "Campo que falló la validación", example = "nombre")
            String field,

            @Schema(description = "Mensaje de error", example = "El nombre es obligatorio")
            String message
    ) {
    }
}