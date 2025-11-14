package com.springback.apimatriculas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${server.servlet.context-path:/api/v1}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:" + serverPort + contextPath);
        localServer.setDescription("Servidor Local de Desarrollo");

        Contact contact = new Contact();
        contact.setName("Equipo de Desarrollo");
        contact.setEmail("desarrollo@universidad.edu.pe");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("API de Sistema de Matrículas Universitarias")
                .version("1.0.0")
                .description("API REST para la gestión de matrículas, facultades y carreras universitarias. " +
                        "Esta API proporciona endpoints para realizar operaciones CRUD sobre las entidades principales del sistema.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
