package com.springback.apimatriculas.util;

public final class Constants {

    private Constants() {
        throw new IllegalStateException("Clase de utilidad - No se puede instanciar");
    }

    // Nombres de recursos
    public static final String FACULTAD = "Facultad";
    public static final String CARRERA = "Carrera";

    // Mensajes de éxito
    public static final String CREATED_SUCCESSFULLY = "creado exitosamente";
    public static final String UPDATED_SUCCESSFULLY = "actualizado exitosamente";
    public static final String DELETED_SUCCESSFULLY = "eliminado exitosamente";

    // Mensajes de error
    public static final String NOT_FOUND = "no encontrado";
    public static final String ALREADY_EXISTS = "ya existe";
    public static final String CANNOT_DELETE = "no se puede eliminar";
    public static final String HAS_DEPENDENCIES = "tiene dependencias asociadas";

    // Campos comunes
    public static final String FIELD_ID = "ID";
    public static final String FIELD_NOMBRE = "nombre";

    // Valores por defecto
    public static final boolean DEFAULT_ACTIVO = true;
}