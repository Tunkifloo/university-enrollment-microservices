FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /build

# Crear POM padre SIN módulos
COPY pom.xml ./pom-original.xml
RUN sed '/<modules>/,/<\/modules>/d' pom-original.xml > pom.xml

# Instalar POM padre
RUN mvn install:install-file \
    -Dfile=pom.xml \
    -DpomFile=pom.xml \
    -DgroupId=com.university \
    -DartifactId=sistema-matriculas-parent \
    -Dversion=1.0.0 \
    -Dpackaging=pom

# Compilar common-lib
COPY Backend/common-lib/pom.xml ./Backend/common-lib/pom.xml
COPY Backend/common-lib/src ./Backend/common-lib/src
RUN mvn clean install -f Backend/common-lib/pom.xml -DskipTests

# Compilar auth-service
COPY Backend/auth-service/pom.xml ./Backend/auth-service/pom.xml
RUN mvn dependency:go-offline -f Backend/auth-service/pom.xml || true
COPY Backend/auth-service/src ./Backend/auth-service/src
RUN mvn clean package -f Backend/auth-service/pom.xml -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/Backend/auth-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]