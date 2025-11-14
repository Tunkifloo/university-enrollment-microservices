package com.springback.apimatriculas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ApiMatriculasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiMatriculasApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("API de Matrículas Universitarias");
        System.out.println("===========================================");
        System.out.println("Swagger UI: http://localhost:8080/api/v1/swagger-ui.html");
        System.out.println("API Docs: http://localhost:8080/api/v1/api-docs");
        System.out.println("Actuator Health: http://localhost:8080/api/v1/actuator/health");
        System.out.println("===========================================\n");
    }
}
