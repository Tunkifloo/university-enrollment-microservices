FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /build

# 1: Crear POM padre SIN módulos
COPY pom.xml ./pom-original.xml

# Crear versión del POM padre sin <modules> para instalación
RUN sed '/<modules>/,/<\/modules>/d' pom-original.xml > pom.xml

# Instalar POM padre en repositorio local
RUN mvn install:install-file \
    -Dfile=pom.xml \
    -DpomFile=pom.xml \
    -DgroupId=com.university \
    -DartifactId=sistema-matriculas-parent \
    -Dversion=1.0.0 \
    -Dpackaging=pom

# 2: Compilar common-lib
COPY Backend/common-lib/pom.xml ./Backend/common-lib/pom.xml
COPY Backend/common-lib/src ./Backend/common-lib/src

RUN mvn clean install -f Backend/common-lib/pom.xml -DskipTests

# 3: Compilar eureka-server
COPY Backend/eureka-server/pom.xml ./Backend/eureka-server/pom.xml

# Descargar dependencias
RUN mvn dependency:go-offline -f Backend/eureka-server/pom.xml || true

COPY Backend/eureka-server/src ./Backend/eureka-server/src

# Compilar el servicio
RUN mvn clean package -f Backend/eureka-server/pom.xml -DskipTests

# Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/Backend/eureka-server/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]