package com.university.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("API Gateway iniciado correctamente");
        System.out.println("Gateway: http://localhost:8080");
        System.out.println("========================================\n");
    }
}