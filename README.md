# Sistema de Matrículas Universitarias

Sistema full-stack para la gestión de matrículas académicas en instituciones de educación superior, construido con arquitectura en capas y patrones de diseño enterprise.

## Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Stack Tecnológico](#stack-tecnológico)
- [Arquitectura del Sistema](#arquitectura-del-sistema)
- [Patrones de Diseño](#patrones-de-diseño)
- [Requisitos Previos](#requisitos-previos)
- [Guía de Instalación con Docker](#guía-de-instalación-con-docker)
- [Instalación Local](#instalación-local)
- [Documentación de Endpoints](#documentación-de-endpoints)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Configuración Avanzada](#configuración-avanzada)
- [Migración de Base de Datos](#migración-de-base-de-datos)

## Descripción General

Aplicación empresarial que permite la gestión integral de facultades y carreras universitarias, implementando las mejores prácticas de desarrollo de software, arquitectura limpia y patrones de diseño robustos. El sistema proporciona una interfaz web intuitiva respaldada por una API RESTful completamente documentada.

## Stack Tecnológico

### Backend

- **Java 17**: Lenguaje principal con características modernas
- **Spring Boot 3.5.6**: Framework enterprise para aplicaciones Java
- **Spring Data JPA**: Abstracción de persistencia con Hibernate
- **PostgreSQL 16**: Sistema de gestión de base de datos relacional
- **Flyway**: Herramienta de migración y versionado de base de datos
- **MapStruct**: Framework de mapeo de objetos tipo-seguro
- **Lombok**: Reducción de código boilerplate
- **Maven**: Gestión de dependencias y construcción del proyecto
- **Swagger/OpenAPI**: Documentación interactiva de la API

### Frontend

- **React 19**: Biblioteca de interfaz de usuario
- **TypeScript 5.9**: JavaScript tipado para desarrollo escalable
- **Vite 7**: Build tool de próxima generación
- **Zustand 5**: Gestión de estado ligera y simple
- **Tailwind CSS 3.4**: Framework CSS utility-first
- **Axios**: Cliente HTTP para comunicación con el backend

### DevOps

- **Docker**: Contenedorización de aplicaciones
- **Docker Compose**: Orquestación de contenedores
- **Nginx**: Servidor web para el frontend en producción

## Arquitectura del Sistema

### Arquitectura General

El sistema implementa una arquitectura de tres capas (Three-Tier Architecture) desacoplada mediante una API REST:

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                     │
│  ┌───────────────────────────────────────────────────────┐  │
│  │           React + TypeScript + Zustand                │  │
│  │              (Single Page Application)                │  │
│  └───────────────────────────────────────────────────────┘  │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTP/REST
                           │ (JSON)
┌──────────────────────────▼──────────────────────────────────┐
│                     CAPA DE LÓGICA                          │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              Spring Boot REST API                     │  │
│  │  ┌─────────────────────────────────────────────────┐  │  │
│  │  │ Controllers → Services → Repositories           │  │  │
│  │  └─────────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────┘  │
└──────────────────────────┬──────────────────────────────────┘
                           │ JDBC
                           │ (Hibernate/JPA)
┌──────────────────────────▼──────────────────────────────────┐
│                     CAPA DE DATOS                           │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              PostgreSQL Database                      │  │
│  │     (Esquema gestionado con Flyway)                   │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Arquitectura Backend (Layered Architecture)

El backend sigue una arquitectura en capas claramente definida:

```
src/main/java/com/springback/apimatriculas/
│
├── controller/              → CAPA DE PRESENTACIÓN
│   ├── CarreraController    (Endpoints REST para Carreras)
│   └── FacultadController   (Endpoints REST para Facultades)
│
├── service/                 → CAPA DE LÓGICA DE NEGOCIO
│   ├── interfaces/
│   │   ├── ICarreraService
│   │   └── IFacultadService
│   └── impl/
│       ├── CarreraServiceImpl    (Lógica de negocio de Carreras)
│       └── FacultadServiceImpl   (Lógica de negocio de Facultades)
│
├── repository/              → CAPA DE ACCESO A DATOS
│   ├── CarreraRepository    (Spring Data JPA Repository)
│   └── FacultadRepository   (Spring Data JPA Repository)
│
├── domain/model/            → CAPA DE DOMINIO
│   ├── Carrera              (Entidad JPA)
│   └── Facultad             (Entidad JPA)
│
├── dto/                     → DATA TRANSFER OBJECTS
│   ├── request/
│   │   ├── CarreraRequestDTO
│   │   └── FacultadRequestDTO
│   ├── response/
│   │   ├── CarreraResponseDTO
│   │   └── FacultadResponseDTO
│   └── mapper/
│       ├── CarreraMapper    (MapStruct Interface)
│       └── FacultadMapper   (MapStruct Interface)
│
├── exception/               → MANEJO DE EXCEPCIONES
│   ├── custom/
│   │   ├── ResourceNotFoundException
│   │   ├── DuplicateResourceException
│   │   └── BusinessRuleException
│   ├── ErrorResponse
│   └── GlobalExceptionHandler
│
├── config/                  → CONFIGURACIONES
│   ├── OpenApiConfig        (Swagger/OpenAPI)
│   └── CorsConfig           (CORS Policy)
│
└── util/                    → UTILIDADES
    └── Constants            (Constantes del sistema)
```

**Responsabilidades por capa:**

1. **Controller Layer**: Maneja las solicitudes HTTP, valida la entrada (usando Bean Validation), delega a los servicios y formatea las respuestas
2. **Service Layer**: Contiene la lógica de negocio, validaciones complejas, y coordina las operaciones entre repositorios
3. **Repository Layer**: Abstracción de acceso a datos usando Spring Data JPA, con queries personalizadas cuando sea necesario
4. **Domain Layer**: Define las entidades del dominio con sus relaciones y restricciones
5. **DTO Layer**: Objetos de transferencia que desacoplan la representación externa de las entidades internas

### Arquitectura Frontend (Feature-Based)

El frontend utiliza una arquitectura modular basada en características:

```
src/
│
├── features/                     → MÓDULOS DE CARACTERÍSTICAS
│   ├── carreras/
│   │   ├── components/           (Componentes específicos de Carreras)
│   │   │   ├── CarreraForm.tsx
│   │   │   ├── CarreraList.tsx
│   │   │   └── CarreraCard.tsx
│   │   ├── hooks/                (Custom Hooks)
│   │   │   └── useCarreraActions.ts
│   │   ├── store/                (Estado local con Zustand)
│   │   │   └── carreraStore.ts
│   │   └── CarrerasPage.tsx      (Página principal)
│   │
│   └── facultades/
│       ├── components/
│       │   ├── FacultadForm.tsx
│       │   ├── FacultadList.tsx
│       │   └── FacultadCard.tsx
│       ├── hooks/
│       │   └── useFacultadActions.ts
│       ├── store/
│       │   └── facultadStore.ts
│       └── FacultadesPage.tsx
│
├── shared/                       → CÓDIGO COMPARTIDO
│   ├── components/               (Componentes reutilizables)
│   │   ├── Button.tsx
│   │   ├── Modal.tsx
│   │   └── LoadingSpinner.tsx
│   ├── config/                   (Configuración)
│   │   └── api.config.ts         (Axios instance)
│   ├── types/                    (Tipos TypeScript)
│   │   └── index.ts
│   └── utils/                    (Utilidades)
│       └── formatters.ts
│
├── App.tsx                       → APLICACIÓN PRINCIPAL
└── main.tsx                      → PUNTO DE ENTRADA
```

## Patrones de Diseño

### Backend

#### 1. Layered Architecture (Arquitectura en Capas)

Separación clara de responsabilidades en capas independientes, facilitando el mantenimiento y las pruebas.

#### 2. Repository Pattern

Abstracción del acceso a datos mediante interfaces de Spring Data JPA:

```java
@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long> {
    Optional<Facultad> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Facultad> findByActivoTrue();
}
```

#### 3. Service Layer Pattern

Encapsulación de la lógica de negocio en servicios reutilizables:

```java
@Service
@RequiredArgsConstructor
public class FacultadServiceImpl implements IFacultadService {
    private final FacultadRepository facultadRepository;
    private final FacultadMapper facultadMapper;
    
    @Transactional
    public FacultadResponseDTO create(FacultadRequestDTO requestDTO) {
        // Lógica de negocio
    }
}
```

#### 4. Data Transfer Object (DTO) Pattern

Separación entre la representación externa e interna de los datos:

- **Request DTOs**: Validación de entrada con Bean Validation
- **Response DTOs**: Formateo de salida
- **Mappers**: Conversión automática con MapStruct

```java
@Mapper(componentModel = "spring")
public interface FacultadMapper {
    Facultad toEntity(FacultadRequestDTO dto);
    FacultadResponseDTO toResponseDTO(Facultad entity);
}
```

#### 5. Dependency Injection

Inyección de dependencias mediante el contenedor IoC de Spring:

```java
@RequiredArgsConstructor  // Constructor injection via Lombok
public class FacultadServiceImpl implements IFacultadService {
    private final FacultadRepository facultadRepository;
    private final FacultadMapper facultadMapper;
}
```

#### 6. Exception Handling Pattern

Manejo centralizado de excepciones con `@ControllerAdvice`:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(...) {
        // Manejo de error
    }
}
```

#### 7. Builder Pattern

Construcción de objetos complejos mediante Records de Java y Lombok:

```java
public record FacultadRequestDTO(
    @NotBlank String nombre,
    @Size(max = 500) String descripcion,
    Boolean activo
) {
    public FacultadRequestDTO {
        if (activo == null) activo = true;
    }
}
```

### Frontend

#### 1. Component-Based Architecture

Interfaz construida con componentes reutilizables de React.

#### 2. Custom Hooks Pattern

Encapsulación de lógica reutilizable:

```typescript
export const useFacultadActions = () => {
  const { setFacultades, addFacultad } = useFacultadStore();
  
  const fetchFacultades = async () => {
    // Lógica de fetch
  };
  
  return { fetchFacultades };
};
```

#### 3. State Management (Flux Pattern)

Gestión de estado global con Zustand:

```typescript
export const useFacultadStore = create<FacultadStore>((set) => ({
  facultades: [],
  setFacultades: (facultades) => set({ facultades }),
  addFacultad: (facultad) => set((state) => ({
    facultades: [...state.facultades, facultad]
  }))
}));
```

#### 4. Container/Presenter Pattern

Separación entre lógica (hooks/stores) y presentación (componentes).

## Requisitos Previos

### Opción 1: Docker (Recomendado)

- Docker Desktop 20.10 o superior
- Docker Compose 2.0 o superior
- 4GB de RAM disponible
- 2GB de espacio en disco

### Opción 2: Instalación Local

- Java JDK 17 o superior
- Maven 3.8 o superior
- Node.js 18 o superior
- npm 9 o superior
- PostgreSQL 16 o superior

## Guía de Instalación con Docker

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Tunkifloo/university-enrollment-system.git
cd university-enrollment-system
```

### 2. Configurar Variables de Entorno

Copiar el archivo de ejemplo y ajustar los valores según sea necesario:

```bash
cp .env.example .env
```

Contenido del archivo `.env`:

```env
# PostgreSQL
POSTGRES_DB=matriculas_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=admin

# Backend
DB_HOST=postgres
DB_PORT=5432
SERVER_PORT=8080
JPA_DDL_AUTO=update

# Frontend
VITE_API_BASE_URL=http://localhost:8080/api/v1
VITE_DEV_PORT=5173
```

### 3. Construir y Levantar los Servicios

Iniciar todos los contenedores en modo detached:

```bash
docker-compose up -d
```

Este comando ejecutará:

1. **Servicio PostgreSQL**: Base de datos en el puerto 5432
2. **Servicio Backend**: API Spring Boot en el puerto 8080
3. **Servicio Frontend**: Aplicación React en el puerto 5173

### 4. Verificar los Servicios

Verificar que todos los contenedores estén corriendo:

```bash
docker-compose ps
```

Salida esperada:

```
NAME                    STATUS              PORTS
matriculas-db           Up 30 seconds       0.0.0.0:5432->5432/tcp
matriculas-backend      Up 25 seconds       0.0.0.0:8080->8080/tcp
matriculas-frontend     Up 20 seconds       0.0.0.0:5173->80/tcp
```

### 5. Acceder a la Aplicación

- **Frontend**: `http://localhost:5173`
- **Backend API**: `http://localhost:8080/api/v1`
- **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui.html`
- **Health Check**: `http://localhost:8080/api/v1/actuator/health`

### Comandos Docker Útiles

```bash
# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f backend

# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes (CUIDADO: elimina datos de la BD)
docker-compose down -v

# Reconstruir y levantar los servicios
docker-compose up -d --build

# Reiniciar un servicio específico
docker-compose restart backend

# Ejecutar comandos dentro de un contenedor
docker-compose exec backend bash
docker-compose exec postgres psql -U postgres -d matriculas_db
```

## Instalación Local

### 1. Configurar Base de Datos

Instalar PostgreSQL 16 y crear la base de datos:

```bash
# Conectar a PostgreSQL
psql -U postgres

# Crear base de datos
CREATE DATABASE matriculas_db;

# Crear usuario (opcional)
CREATE USER matriculas_user WITH PASSWORD 'secure_password';
GRANT ALL PRIVILEGES ON DATABASE matriculas_db TO matriculas_user;

# Salir
\q
```

### 2. Configurar y Ejecutar el Backend

```bash
# Navegar al directorio del backend
cd Backend/API-matriculas

# Copiar archivo de configuración
cp .env.example .env

# Editar .env con tus credenciales de base de datos
nano .env

# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

Alternativamente, ejecutar el JAR compilado:

```bash
mvn clean package
java -jar target/API-matriculas-0.0.1-SNAPSHOT.jar
```

Verificar que el backend esté funcionando:

```bash
curl http://localhost:8080/api/v1/actuator/health
```

### 3. Configurar y Ejecutar el Frontend

```bash
# Navegar al directorio del frontend
cd Frontend

# Crear archivo de configuración
echo "VITE_API_BASE_URL=http://localhost:8080/api/v1" > .env

# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
npm run dev
```

El frontend estará disponible en `http://localhost:5173`

### 4. Build de Producción (Frontend)

```bash
# Construir para producción
npm run build

# Previsualizar la build
npm run preview
```

## Documentación de Endpoints

### URL Base

```
http://localhost:8080/api/v1
```

### Facultades

#### Obtener todas las facultades

```http
GET /facultades
```

**Respuesta exitosa (200 OK):**

```json
[
  {
    "facultadId": 1,
    "nombre": "Facultad de Ingeniería",
    "descripcion": "Facultad dedicada a la formación de ingenieros",
    "ubicacion": "Pabellón A - Campus Principal",
    "decano": "Dr. Juan Pérez Rodríguez",
    "fechaRegistro": "2025-01-15T10:30:00",
    "activo": true,
    "cantidadCarreras": 4
  }
]
```

#### Obtener facultad por ID

```http
GET /facultades/{id}
```

**Parámetros:**
- `id` (path): ID de la facultad

**Respuesta exitosa (200 OK):**

```json
{
  "facultadId": 1,
  "nombre": "Facultad de Ingeniería",
  "descripcion": "Facultad dedicada a la formación de ingenieros",
  "ubicacion": "Pabellón A - Campus Principal",
  "decano": "Dr. Juan Pérez Rodríguez",
  "fechaRegistro": "2025-01-15T10:30:00",
  "activo": true,
  "cantidadCarreras": 4
}
```

**Respuesta de error (404 Not Found):**

```json
{
  "status": 404,
  "message": "Recurso no encontrado",
  "details": "Facultad con ID 999 no encontrada",
  "timestamp": "2025-10-20T14:30:00",
  "path": "/api/v1/facultades/999"
}
```

#### Crear nueva facultad

```http
POST /facultades
Content-Type: application/json
```

**Body:**

```json
{
  "nombre": "Facultad de Arquitectura",
  "descripcion": "Facultad de diseño arquitectónico",
  "ubicacion": "Pabellón F",
  "decano": "Arq. María López",
  "activo": true
}
```

**Validaciones:**
- `nombre`: Requerido, entre 3 y 100 caracteres, único
- `descripcion`: Opcional, máximo 500 caracteres
- `ubicacion`: Opcional, máximo 100 caracteres
- `decano`: Opcional, máximo 100 caracteres
- `activo`: Opcional, por defecto `true`

**Respuesta exitosa (201 Created):**

```json
{
  "facultadId": 6,
  "nombre": "Facultad de Arquitectura",
  "descripcion": "Facultad de diseño arquitectónico",
  "ubicacion": "Pabellón F",
  "decano": "Arq. María López",
  "fechaRegistro": "2025-10-20T14:30:00",
  "activo": true,
  "cantidadCarreras": 0
}
```

**Respuesta de error (409 Conflict):**

```json
{
  "status": 409,
  "message": "Recurso duplicado",
  "details": "Ya existe una Facultad con nombre 'Facultad de Arquitectura'",
  "timestamp": "2025-10-20T14:30:00",
  "path": "/api/v1/facultades"
}
```

#### Actualizar facultad

```http
PUT /facultades/{id}
Content-Type: application/json
```

**Body:**

```json
{
  "nombre": "Facultad de Ingeniería y Tecnología",
  "descripcion": "Facultad modernizada",
  "ubicacion": "Pabellón A - Campus Principal",
  "decano": "Dr. Juan Pérez Rodríguez",
  "activo": true
}
```

**Respuesta exitosa (200 OK):** Igual estructura que GET

#### Eliminar facultad (eliminación lógica)

```http
DELETE /facultades/{id}
```

**Respuesta exitosa (204 No Content):** Sin contenido

### Carreras

#### Obtener todas las carreras

```http
GET /carreras
```

**Respuesta exitosa (200 OK):**

```json
[
  {
    "carreraId": 1,
    "facultadId": 1,
    "nombreFacultad": "Facultad de Ingeniería",
    "nombre": "Ingeniería de Sistemas",
    "descripcion": "Carrera profesional enfocada en desarrollo de software",
    "duracionSemestres": 10,
    "tituloOtorgado": "Ingeniero de Sistemas",
    "fechaRegistro": "2025-01-15T10:30:00",
    "activo": true
  }
]
```

#### Obtener carreras por facultad

```http
GET /carreras/facultad/{facultadId}
```

**Parámetros:**
- `facultadId` (path): ID de la facultad

#### Obtener carrera por ID

```http
GET /carreras/{id}
```

#### Crear nueva carrera

```http
POST /carreras
Content-Type: application/json
```

**Body:**

```json
{
  "facultadId": 1,
  "nombre": "Ingeniería de Software",
  "descripcion": "Carrera especializada en ingeniería de software",
  "duracionSemestres": 10,
  "tituloOtorgado": "Ingeniero de Software",
  "activo": true
}
```

**Validaciones:**
- `facultadId`: Requerido, debe existir y estar activa
- `nombre`: Requerido, entre 3 y 100 caracteres, único
- `descripcion`: Opcional, máximo 500 caracteres
- `duracionSemestres`: Requerido, entre 1 y 20
- `tituloOtorgado`: Opcional, máximo 100 caracteres
- `activo`: Opcional, por defecto `true`

**Respuesta exitosa (201 Created):** Similar a GET carrera

#### Actualizar carrera

```http
PUT /carreras/{id}
Content-Type: application/json
```

#### Eliminar carrera (eliminación lógica)

```http
DELETE /carreras/{id}
```

### Documentación Interactiva

Para explorar todos los endpoints de forma interactiva con Swagger UI:

```
http://localhost:8080/api/v1/swagger-ui.html
```

## Estructura del Proyecto

```
university-enrollment-system/
├── Backend/
│   └── API-matriculas/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/com/springback/apimatriculas/
│       │   │   │   ├── controller/
│       │   │   │   │   ├── CarreraController.java
│       │   │   │   │   └── FacultadController.java
│       │   │   │   ├── service/
│       │   │   │   │   ├── interfaces/
│       │   │   │   │   │   ├── ICarreraService.java
│       │   │   │   │   │   └── IFacultadService.java
│       │   │   │   │   └── impl/
│       │   │   │   │       ├── CarreraServiceImpl.java
│       │   │   │   │       └── FacultadServiceImpl.java
│       │   │   │   ├── repository/
│       │   │   │   │   ├── CarreraRepository.java
│       │   │   │   │   └── FacultadRepository.java
│       │   │   │   ├── domain/model/
│       │   │   │   │   ├── Carrera.java
│       │   │   │   │   └── Facultad.java
│       │   │   │   ├── dto/
│       │   │   │   │   ├── request/
│       │   │   │   │   │   ├── CarreraRequestDTO.java
│       │   │   │   │   │   └── FacultadRequestDTO.java
│       │   │   │   │   ├── response/
│       │   │   │   │   │   ├── CarreraResponseDTO.java
│       │   │   │   │   │   └── FacultadResponseDTO.java
│       │   │   │   │   └── mapper/
│       │   │   │   │       ├── CarreraMapper.java
│       │   │   │   │       └── FacultadMapper.java
│       │   │   │   ├── exception/
│       │   │   │   │   ├── custom/
│       │   │   │   │   │   ├── ResourceNotFoundException.java
│       │   │   │   │   │   ├── DuplicateResourceException.java
│       │   │   │   │   │   └── BusinessRuleException.java
│       │   │   │   │   ├── ErrorResponse.java
│       │   │   │   │   └── GlobalExceptionHandler.java
│       │   │   │   ├── config/
│       │   │   │   │   ├── OpenApiConfig.java
│       │   │   │   │   └── CorsConfig.java
│       │   │   │   └── util/
│       │   │   │       └── Constants.java
│       │   │   └── resources/
│       │   │       ├── application.properties
│       │   │       └── db/migration/
│       │   │           ├── V1__create_tables.sql
│       │   │           └── V2__insert_data.sql
│       │   └── test/
│       ├── Dockerfile
│       ├── pom.xml
│       └── README.md
├── Frontend/
│   ├── src/
│   │   ├── features/
│   │   │   ├── carreras/
│   │   │   │   ├── components/
│   │   │   │   │   ├── CarreraForm.tsx
│   │   │   │   │   ├── CarreraList.tsx
│   │   │   │   │   └── CarreraCard.tsx
│   │   │   │   ├── hooks/
│   │   │   │   │   └── useCarreraActions.ts
│   │   │   │   ├── store/
│   │   │   │   │   └── carreraStore.ts
│   │   │   │   └── CarrerasPage.tsx
│   │   │   └── facultades/
│   │   │       ├── components/
│   │   │       │   ├── FacultadForm.tsx
│   │   │       │   ├── FacultadList.tsx
│   │   │       │   └── FacultadCard.tsx
│   │   │       ├── hooks/
│   │   │       │   └── useFacultadActions.ts
│   │   │       ├── store/
│   │   │       │   └── facultadStore.ts
│   │   │       └── FacultadesPage.tsx
│   │   ├── shared/
│   │   │   ├── components/
│   │   │   │   ├── Button.tsx
│   │   │   │   ├── Modal.tsx
│   │   │   │   └── LoadingSpinner.tsx
│   │   │   ├── config/
│   │   │   │   └── api.config.ts
│   │   │   └── types/
│   │   │       └── index.ts
│   │   ├── App.tsx
│   │   ├── main.tsx
│   │   └── index.css
│   ├── public/
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   ├── tsconfig.json
│   ├── vite.config.ts
│   └── README.md
├── docker-compose.yml
├── .env.example
├── .env
├── .gitignore
├── LICENSE
└── README.md
```

## Configuración Avanzada

### Variables de Entorno del Backend

El archivo `Backend/API-matriculas/src/main/resources/application.properties` utiliza las siguientes variables:

```properties
# Servidor
SERVER_PORT=8080                           # Puerto del servidor
SERVER_CONTEXT_PATH=/api/v1                # Contexto de la aplicación

# Base de datos
DB_HOST=localhost                          # Host de PostgreSQL
DB_PORT=5432                               # Puerto de PostgreSQL
DB_NAME=matriculas_db                      # Nombre de la base de datos
DB_USERNAME=postgres                       # Usuario de la base de datos
DB_PASSWORD=admin                          # Contraseña de la base de datos

# Pool de conexiones
DB_POOL_MAX_SIZE=10                        # Tamaño máximo del pool
DB_POOL_MIN_IDLE=5                         # Conexiones mínimas inactivas
DB_POOL_TIMEOUT=30000                      # Timeout de conexión (ms)

# JPA/Hibernate
JPA_DDL_AUTO=update                        # Estrategia DDL: update/create/validate
JPA_SHOW_SQL=true                          # Mostrar SQL en logs
JPA_FORMAT_SQL=true                        # Formatear SQL en logs

# Logging
LOG_LEVEL_ROOT=INFO                        # Nivel de log raíz
LOG_LEVEL_APP=DEBUG                        # Nivel de log de la aplicación
LOG_LEVEL_WEB=DEBUG                        # Nivel de log web
LOG_LEVEL_SQL=DEBUG                        # Nivel de log SQL

# Actuator
ACTUATOR_ENDPOINTS=health,info,metrics     # Endpoints expuestos
ACTUATOR_SHOW_DETAILS=always               # Mostrar detalles de health

# Aplicación
APP_NAME=API Sistema de Matrículas         # Nombre de la aplicación
APP_VERSION=1.0.0                          # Versión de la aplicación
APP_TIMEZONE=America/Lima                  # Zona horaria

# Swagger
SWAGGER_ENABLED=true                       # Habilitar Swagger UI
SWAGGER_PATH=/swagger-ui.html              # Ruta de Swagger UI
```

### Variables de Entorno del Frontend

El archivo `Frontend/.env` utiliza:

```env
VITE_API_BASE_URL=http://localhost:8080/api/v1  # URL base del backend
VITE_DEV_PORT=5173                               # Puerto de desarrollo
VITE_APP_MODE=development                        # Modo de la aplicación
VITE_APP_NAME=Sistema de Matrículas              # Nombre de la aplicación
VITE_APP_VERSION=1.0.0                           # Versión de la aplicación
VITE_ENABLE_LOGS=true                            # Habilitar logs
```

### Configuración de Docker Compose

El archivo `docker-compose.yml` orquesta los tres servicios:

```yaml
services:
  postgres:
    image: postgres:16-alpine
    container_name: matriculas-db
    restart: unless-stopped
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    ports:
      - "${DB_PORT}:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U ${POSTGRES_USER}" ]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app-network

  backend:
    build: Backend/matriculas-service
    container_name: matriculas-backend
    restart: unless-stopped
    env_file: .env
    ports:
      - "${SERVER_PORT}:8080"
    depends_on:
      postgres:
        condition: service_healthy
    networks:
      - app-network

  frontend:
    build:
      context: ./Frontend
      args:
        - VITE_API_BASE_URL=${VITE_API_BASE_URL}
    container_name: matriculas-frontend
    restart: unless-stopped
    ports:
      - "${VITE_DEV_PORT}:80"
    depends_on:
      - backend
    networks:
      - app-network

volumes:
  postgres_data:

networks:
  app-network:
    driver: bridge
```

**Características destacadas:**

- **Health checks**: El backend espera a que PostgreSQL esté saludable antes de iniciar
- **Restart policy**: Los contenedores se reinician automáticamente si fallan
- **Named volumes**: Los datos de PostgreSQL persisten entre reinicios
- **Custom network**: Los servicios se comunican en una red privada

### Configuración de CORS

El backend permite solicitudes desde el frontend mediante configuración CORS:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
```

## Migración de Base de Datos

El proyecto utiliza **Flyway** para gestionar las migraciones de base de datos de forma versionada y controlada.

### Ubicación de las Migraciones

```
Backend/API-matriculas/src/main/resources/db/migration/
├── V1__create_tables.sql      # Creación de tablas iniciales
└── V2__insert_data.sql        # Datos de prueba iniciales
```

### V1: Creación de Tablas

```sql
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
    CONSTRAINT fk_facultad FOREIGN KEY (facultad_id) 
        REFERENCES facultad(facultad_id) ON DELETE RESTRICT,
    CONSTRAINT uk_carrera_nombre UNIQUE (nombre),
    CONSTRAINT ck_duracion_semestres CHECK (duracion_semestres > 0)
);

-- Índices para optimización
CREATE INDEX idx_carrera_facultad ON carrera(facultad_id);
CREATE INDEX idx_facultad_nombre ON facultad(nombre);
CREATE INDEX idx_carrera_nombre ON carrera(nombre);
CREATE INDEX idx_facultad_activo ON facultad(activo);
CREATE INDEX idx_carrera_activo ON carrera(activo);
```

**Características del esquema:**

- **Claves primarias**: IDs autoincrementales (SERIAL)
- **Restricciones de integridad**: Foreign keys con ON DELETE RESTRICT
- **Validaciones**: Checks para duracion_semestres > 0
- **Índices**: Optimización para búsquedas frecuentes
- **Soft delete**: Campo `activo` para eliminación lógica

### V2: Datos de Prueba

```sql
-- Inserción de 5 facultades
INSERT INTO facultad (nombre, descripcion, ubicacion, decano, activo) VALUES
    ('Facultad de Ingeniería', 'Facultad dedicada a la formación de ingenieros', 
     'Pabellón A - Campus Principal', 'Dr. Juan Pérez Rodríguez', true),
    ('Facultad de Ciencias', 'Facultad enfocada en ciencias básicas', 
     'Pabellón B - Campus Principal', 'Dra. María González López', true),
    -- ... más facultades
;

-- Inserción de carreras por facultad
INSERT INTO carrera (facultad_id, nombre, descripcion, duracion_semestres, 
                     titulo_otorgado, activo) VALUES
    (1, 'Ingeniería de Sistemas', 'Desarrollo de software y sistemas', 
     10, 'Ingeniero de Sistemas', true),
    (1, 'Ingeniería Industrial', 'Optimización de procesos industriales', 
     10, 'Ingeniero Industrial', true),
    -- ... más carreras
;
```

### Crear Nueva Migración

Para agregar una nueva migración:

1. Crear archivo con el formato: `V{VERSION}__{descripcion}.sql`
   - Ejemplo: `V3__add_estudiantes_table.sql`

2. El número de versión debe ser secuencial

3. Flyway ejecutará automáticamente la migración al iniciar el backend

```sql
-- V3__add_estudiantes_table.sql
CREATE TABLE IF NOT EXISTS estudiante (
    estudiante_id SERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);
```

### Verificar Estado de Migraciones

Flyway mantiene un historial en la tabla `flyway_schema_history`:

```sql
SELECT version, description, installed_on, success 
FROM flyway_schema_history 
ORDER BY installed_rank;
```

## Modelo de Datos

### Diagrama Entidad-Relación

```
┌─────────────────────────────────┐
│          FACULTAD               │
├─────────────────────────────────┤
│ PK facultad_id: SERIAL          │
│    nombre: VARCHAR(100) UNIQUE  │
│    descripcion: TEXT            │
│    ubicacion: VARCHAR(100)      │
│    decano: VARCHAR(100)         │
│    fecha_registro: TIMESTAMP    │
│    activo: BOOLEAN              │
└────────────┬────────────────────┘
             │
             │ 1:N
             │
┌────────────▼────────────────────┐
│           CARRERA               │
├─────────────────────────────────┤
│ PK carrera_id: SERIAL           │
│ FK facultad_id: INTEGER         │
│    nombre: VARCHAR(100) UNIQUE  │
│    descripcion: TEXT            │
│    duracion_semestres: INTEGER  │
│    titulo_otorgado: VARCHAR(100)│
│    fecha_registro: TIMESTAMP    │
│    activo: BOOLEAN              │
└─────────────────────────────────┘
```

### Relaciones

- Una **Facultad** puede tener muchas **Carreras** (1:N)
- Una **Carrera** pertenece a una única **Facultad**
- La eliminación de una Facultad está restringida si tiene Carreras asociadas (ON DELETE RESTRICT)

## Testing

### Backend

Ejecutar tests unitarios y de integración:

```bash
cd Backend/API-matriculas
mvn test
```

Ejecutar tests con cobertura:

```bash
mvn clean test jacoco:report
```

El reporte de cobertura se genera en: `target/site/jacoco/index.html`

### Frontend

Ejecutar tests:

```bash
cd Frontend
npm test
```

Ejecutar tests con cobertura:

```bash
npm run test:coverage
```

## Seguridad

### Validaciones

**Backend:**
- Bean Validation (JSR-380) en DTOs
- Validaciones de negocio en la capa de servicio
- SQL injection prevention mediante JPA/Hibernate prepared statements

**Frontend:**
- Validación de formularios en tiempo real
- Sanitización de inputs del usuario
- TypeScript para type safety

### Headers de Seguridad

El backend configura headers HTTP seguros:

```properties
# application.properties
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.enabled=true
server.servlet.encoding.force=true
```

### HTTPS (Producción)

Para producción, se recomienda:

1. Configurar SSL/TLS en el servidor
2. Usar un reverse proxy (Nginx, Apache)
3. Implementar rate limiting
4. Agregar autenticación y autorización (Spring Security + JWT)

## Monitoreo

### Actuator Endpoints

El backend expone endpoints de monitoreo:

```bash
# Health check
curl http://localhost:8080/api/v1/actuator/health

# Información de la aplicación
curl http://localhost:8080/api/v1/actuator/info

# Métricas
curl http://localhost:8080/api/v1/actuator/metrics
```

### Logs

**Backend:**
```bash
# Ver logs en Docker
docker-compose logs -f backend

# Archivo de logs (instalación local)
tail -f logs/spring-boot-application.log
```

**Frontend:**
```bash
# Ver logs en Docker
docker-compose logs -f frontend

# Logs del navegador
# Abrir DevTools (F12) → Console
```

## Performance

### Optimizaciones Backend

1. **Índices de base de datos** en columnas frecuentemente consultadas
2. **Connection pooling** con HikariCP
3. **Lazy loading** de relaciones JPA
4. **Query optimization** con Spring Data JPA
5. **Caching** (preparado para Redis/Caffeine)

### Optimizaciones Frontend

1. **Code splitting** automático con Vite
2. **Lazy loading** de componentes
3. **Optimización de bundle** con tree-shaking
4. **Minificación** en producción
5. **Caching** de requests HTTP

## Troubleshooting

### Backend no inicia

**Problema:** Error de conexión a base de datos

```
Solución:
1. Verificar que PostgreSQL esté corriendo
2. Revisar credenciales en .env
3. Comprobar que el puerto 5432 esté disponible
4. Verificar logs: docker-compose logs postgres
```

**Problema:** Puerto 8080 en uso

```
Solución:
1. Cambiar SERVER_PORT en .env
2. Actualizar VITE_API_BASE_URL en frontend
3. Reiniciar servicios: docker-compose restart
```

### Frontend no carga

**Problema:** Error de CORS

```
Solución:
1. Verificar VITE_API_BASE_URL en .env
2. Confirmar que backend está corriendo
3. Revisar CorsConfig.java para allowed origins
```

**Problema:** Puerto 5173 en uso

```
Solución:
1. Cambiar VITE_DEV_PORT en .env
2. Reiniciar frontend: docker-compose restart frontend
```

### Base de datos

**Problema:** Migraciones de Flyway fallan

```
Solución:
1. Revisar logs: docker-compose logs backend
2. Verificar sintaxis SQL en archivos de migración
3. Restaurar estado limpio:
   docker-compose down -v
   docker-compose up -d
```

**Problema:** Datos no persisten

```
Solución:
1. Verificar que el volumen postgres_data exista
2. No usar flag -v al hacer docker-compose down
3. Backup de datos antes de operaciones destructivas
```

## Contribución

1. Fork el repositorio
2. Crear una rama para tu feature: `git checkout -b feature/nueva-funcionalidad`
3. Commit tus cambios: `git commit -m 'Agregar nueva funcionalidad'`
4. Push a la rama: `git push origin feature/nueva-funcionalidad`
5. Abrir un Pull Request

### Estándares de Código

**Backend:**
- Seguir convenciones de Java (camelCase, PascalCase)
- Usar Lombok para reducir boilerplate
- Documentar métodos públicos con Javadoc
- Mantener cobertura de tests > 80%

**Frontend:**
- Seguir convenciones de TypeScript
- Usar functional components y hooks
- Mantener componentes pequeños y reutilizables
- Escribir tests para lógica crítica

## Roadmap

### Versión 1.1

- Autenticación y autorización con Spring Security + JWT
- Sistema de roles (Admin, Coordinador, Estudiante)
- Módulo de estudiantes y matrículas

### Versión 1.2

- Generación de reportes en PDF
- Dashboard con estadísticas
- Notificaciones por email

### Versión 2.0

- Sistema de calificaciones
- Módulo de pagos
- Aplicación móvil (React Native)

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

```
MIT License

Copyright (c) 2025 Tunkifloo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

## Autor

**Tunkifloo**

- GitHub: [@Tunkifloo](https://github.com/Tunkifloo)
- Proyecto: [university-enrollment-system](https://github.com/Tunkifloo/university-enrollment-system)

## Agradecimientos

- Spring Framework Team
- React Team
- PostgreSQL Global Development Group
- Comunidad Open Source

## Soporte

Para reportar bugs o solicitar features, por favor abre un issue en:
https://github.com/Tunkifloo/university-enrollment-system/issues

---

**Última actualización:** Octubre 2025  
**Versión:** 1.0.0
