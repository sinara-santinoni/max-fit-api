package com.maxfit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração global de CORS (Cross-Origin Resource Sharing)
 * Permite que o frontend acesse a API de diferentes origens
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins:http://localhost:5173,http://localhost:3000}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica CORS apenas para endpoints da API
                .allowedOrigins(
                        // ===== Ambientes de Desenvolvimento Local =====
                        "http://localhost:3000",      // React padrão
                        "http://localhost:5173",      // Vite padrão
                        "http://localhost:5174",      // Vite alternativo
                        "http://localhost:5175",      // Vite alternativo
                        "http://localhost:5500",      // Live Server
                        "http://localhost:8080",      // Backend local (para testes)

                        // ===== Ambientes de Produção/Staging =====
                        "https://cheerful-klepon-54ef0e.netlify.app",
                        "https://maxfit-tcc.onrender.com",
                        "https://lolly-mandzi-c58daa.netlify.app",
                        "https://jolly-mandazi-c85040.netlify.app",
                        "https://curious-bunny-7781a5.netlify.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders(
                        "Authorization",
                        "Content-Type",
                        "X-Requested-With",
                        "Accept",
                        "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers"
                )
                .exposedHeaders(
                        "Authorization",
                        "Content-Disposition"
                )
                .allowCredentials(true) // Permite cookies e autenticação
                .maxAge(3600); // Cache de preflight por 1 hora
    }
}