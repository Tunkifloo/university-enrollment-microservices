package com.university.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Email Service iniciado correctamente");
        System.out.println("API: http://localhost:8083");
        System.out.println("Consumiendo mensajes de RabbitMQ");
        System.out.println("========================================\n");
    }
}
