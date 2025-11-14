-- Creación de la base de datos (ejecutar primero manualmente)
-- CREATE DATABASE matriculas_db;

-- Tabla FACULTAD
CREATE TABLE IF NOT EXISTS facultad (
                                        facultad_id SERIAL PRIMARY KEY,
                                        nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    ubicacion VARCHAR(100),
    decano VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
    );

-- Tabla CARRERA
CREATE TABLE IF NOT EXISTS carrera (
                                       carrera_id SERIAL PRIMARY KEY,
                                       facultad_id INTEGER NOT NULL,
                                       nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    duracion_semestres INTEGER NOT NULL,
    titulo_otorgado VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_facultad FOREIGN KEY (facultad_id) REFERENCES facultad(facultad_id) ON DELETE RESTRICT,
    CONSTRAINT uk_carrera_nombre UNIQUE (nombre),
    CONSTRAINT ck_duracion_semestres CHECK (duracion_semestres > 0)
    );

-- Índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_carrera_facultad ON carrera(facultad_id);
CREATE INDEX IF NOT EXISTS idx_facultad_nombre ON facultad(nombre);
CREATE INDEX IF NOT EXISTS idx_carrera_nombre ON carrera(nombre);
CREATE INDEX IF NOT EXISTS idx_facultad_activo ON facultad(activo);
CREATE INDEX IF NOT EXISTS idx_carrera_activo ON carrera(activo);

-- Comentarios en las tablas
COMMENT ON TABLE facultad IS 'Tabla que almacena las facultades de la universidad';
COMMENT ON TABLE carrera IS 'Tabla que almacena las carreras profesionales';

COMMENT ON COLUMN facultad.facultad_id IS 'Identificador único de la facultad';
COMMENT ON COLUMN facultad.nombre IS 'Nombre de la facultad';
COMMENT ON COLUMN facultad.activo IS 'Indica si la facultad está activa';

COMMENT ON COLUMN carrera.carrera_id IS 'Identificador único de la carrera';
COMMENT ON COLUMN carrera.facultad_id IS 'Referencia a la facultad';
COMMENT ON COLUMN carrera.duracion_semestres IS 'Duración de la carrera en semestres';
COMMENT ON COLUMN carrera.activo IS 'Indica si la carrera está activa';