-- SCRIPT DE INSERCIÓN DE DATOS DE PRUEBA
-- Sistema de Matrículas Universitarias

-- 1. LIMPIAR TODAS LAS TABLAS Y REINICIAR SECUENCIAS
TRUNCATE TABLE carrera, facultad RESTART IDENTITY CASCADE;

-- 2. INSERTAR FACULTADES
INSERT INTO facultad (nombre, descripcion, ubicacion, decano, activo) VALUES
                                                                          ('Facultad de Ingeniería', 'Facultad dedicada a la formación de ingenieros en diversas especialidades', 'Pabellón A - Campus Principal', 'Dr. Juan Pérez Rodríguez', true),
                                                                          ('Facultad de Ciencias', 'Facultad enfocada en ciencias básicas y aplicadas', 'Pabellón B - Campus Principal', 'Dra. María González López', true),
                                                                          ('Facultad de Medicina', 'Facultad de ciencias de la salud y medicina', 'Pabellón C - Campus Salud', 'Dr. Carlos Sánchez Torres', true),
                                                                          ('Facultad de Derecho', 'Facultad de ciencias jurídicas y sociales', 'Pabellón D - Campus Central', 'Dr. Roberto Díaz Martínez', true),
                                                                          ('Facultad de Educación', 'Facultad de ciencias de la educación', 'Pabellón E - Campus Norte', 'Dra. Ana Fernández Silva', true);

-- 3. VERIFICAR FACULTADES INSERTADAS
SELECT facultad_id, nombre, decano FROM facultad ORDER BY facultad_id;

-- 4. INSERTAR CARRERAS
INSERT INTO carrera (facultad_id, nombre, descripcion, duracion_semestres, titulo_otorgado, activo) VALUES
-- Carreras de Facultad de Ingeniería (ID=1)
(1, 'Ingeniería de Sistemas', 'Carrera profesional enfocada en el desarrollo de software y sistemas de información', 10, 'Ingeniero de Sistemas', true),
(1, 'Ingeniería Industrial', 'Carrera profesional enfocada en la optimización de procesos industriales', 10, 'Ingeniero Industrial', true),
(1, 'Ingeniería Civil', 'Carrera profesional enfocada en diseño y construcción de infraestructura', 10, 'Ingeniero Civil', true),
(1, 'Ingeniería Electrónica', 'Carrera profesional enfocada en electrónica y telecomunicaciones', 10, 'Ingeniero Electrónico', true),

-- Carreras de Facultad de Ciencias (ID=2)
(2, 'Matemáticas', 'Carrera profesional enfocada en matemática pura y aplicada', 10, 'Licenciado en Matemáticas', true),
(2, 'Física', 'Carrera profesional enfocada en física teórica y experimental', 10, 'Licenciado en Física', true),
(2, 'Química', 'Carrera profesional enfocada en química y procesos químicos', 10, 'Licenciado en Química', true),

-- Carreras de Facultad de Medicina (ID=3)
(3, 'Medicina Humana', 'Carrera profesional enfocada en ciencias médicas', 14, 'Médico Cirujano', true),
(3, 'Enfermería', 'Carrera profesional enfocada en cuidados de enfermería', 10, 'Licenciado en Enfermería', true),

-- Carreras de Facultad de Derecho (ID=4)
(4, 'Derecho', 'Carrera profesional enfocada en ciencias jurídicas', 10, 'Abogado', true),

-- Carreras de Facultad de Educación (ID=5)
(5, 'Educación Primaria', 'Carrera profesional enfocada en educación inicial y primaria', 10, 'Licenciado en Educación Primaria', true),
(5, 'Educación Secundaria', 'Carrera profesional enfocada en educación secundaria', 10, 'Licenciado en Educación Secundaria', true);

-- 5. VERIFICAR CARRERAS INSERTADAS
SELECT carrera_id, nombre, duracion_semestres, titulo_otorgado FROM carrera ORDER BY carrera_id;

-- 6. CONSULTA DE VERIFICACIÓN - Ver carreras con sus facultades
SELECT
    c.carrera_id,
    f.nombre AS facultad,
    c.nombre AS carrera,
    c.duracion_semestres,
    c.titulo_otorgado,
    c.activo
FROM carrera c
         JOIN facultad f ON c.facultad_id = f.facultad_id
ORDER BY f.facultad_id, c.nombre;

-- 7. RESUMEN DE DATOS
SELECT
    f.facultad_id,
    f.nombre AS facultad,
    f.decano,
    COUNT(c.carrera_id) AS total_carreras
FROM facultad f
         LEFT JOIN carrera c ON f.facultad_id = c.facultad_id
GROUP BY f.facultad_id, f.nombre, f.decano
ORDER BY f.facultad_id;