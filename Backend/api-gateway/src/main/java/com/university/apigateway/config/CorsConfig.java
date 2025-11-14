package com.university.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Orígenes permitidos
        corsConfig.addAllowedOriginPattern("*");

        // Métodos HTTP permitidos
        corsConfig.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        // Headers permitidos
        corsConfig.setAllowedHeaders(List.of("*"));

        // Permitir credenciales
        corsConfig.setAllowCredentials(true);

        // Exponer headers
        corsConfig.setExposedHeaders(Arrays.asList(
                "Authorization",
                "X-User-Id",
                "X-User-Email",
                "X-User-Role"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}