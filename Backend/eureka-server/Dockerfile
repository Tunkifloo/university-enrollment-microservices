FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY ../../pom.xml ./pom.xml
COPY pom.xml ./eureka-server/pom.xml
COPY Backend/common-lib ./common-lib
COPY src ./eureka-server/src
RUN mvn clean package -pl eureka-server -am -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/Backend/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]