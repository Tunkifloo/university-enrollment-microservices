package com.university.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Auth Service iniciado correctamente");
        System.out.println("API: http://localhost:8082");
        System.out.println("========================================\n");
    }
}