# Backend - API de Matrículas

API RESTful con Spring Boot para gestionar facultades y carreras universitarias.

## Tecnologías

Java 17, Spring Boot 3.5.6, Spring Data JPA, PostgreSQL 16, Flyway, MapStruct, Lombok, Maven

## Pre-requisitos

- Java JDK 17+
- Maven 3.8+
- PostgreSQL 16+

## Instalación

> **Nota:** Si ejecutas el backend sin Docker, configura las variables de entorno en el archivo `.env` de la raíz del proyecto o como variables del sistema.

### 1. Configurar Base de Datos

```sql
CREATE DATABASE matriculas_db;
```

### 2. Compilar y Ejecutar

```bash
cd Backend/API-matriculas
mvn clean install
mvn spring-boot:run
```

O ejecutar el JAR:

```bash
mvn clean package
java -jar target/API-matriculas-0.0.1-SNAPSHOT.jar
```

### 3. Verificar

```bash
curl http://localhost:8080/api/v1/actuator/health
```

## Endpoints

**Base URL:** `http://localhost:8080/api/v1`

| Recurso | Métodos Disponibles |
|---------|---------------------|
| `/facultades` | GET, POST, PUT, DELETE |
| `/carreras` | GET, POST, PUT, DELETE |
| `/carreras/facultad/{id}` | GET |

**Documentación Swagger:** `http://localhost:8080/api/v1/swagger-ui.html`

## Arquitectura

```text
src/main/java/com/springback/apimatriculas/
├── controller/          # Controladores REST
├── service/             # Lógica de negocio
├── repository/          # Repositorios JPA
├── domain/model/        # Entidades
├── dto/                 # DTOs y Mappers
├── exception/           # Manejo de excepciones
└── config/              # Configuraciones
```

## Configuración

Las variables de entorno principales están en `application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/matriculas_db
spring.jpa.hibernate.ddl-auto=update
```

## Docker

```bash
docker build -t matriculas-backend .
docker run -p 8080:8080 matriculas-backend
```

## Tests

```bash
mvn test
```

## Troubleshooting

**Puerto en uso:** Cambia `server.port` en `application.properties`

**Error de BD:** Verifica que PostgreSQL esté corriendo

---

[← Volver al README principal](../README.md)
