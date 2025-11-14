package com.university.auditservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuditServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuditServiceApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Audit Service iniciado correctamente");
        System.out.println("API: http://localhost:8084");
        System.out.println("Consumiendo eventos de Kafka");
        System.out.println("========================================\n");
    }
}