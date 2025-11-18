package com.maxfit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica o CORS para todos os endpoints da API
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://localhost:5174",
                        "http://localhost:5500",
                        "http://localhost:3000",
                        "http://localhost:5175", // Localhost para testes
                        "https://cheerful-klepon-54ef0e.netlify.app",
                        "https://maxfit-tcc.onrender.com",
                        "https://lolly-mandzi-c58daa.netlify.app",
                        "https://jolly-mandazi-c85040.netlify.app",
                        "https://curious-bunny-7781a5.netlify.app" // <-- NOVA URL DO NETLIFY ADICIONADA
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true) // Necessário se você estiver usando cookies ou sessões
                .maxAge(3600); // Cache da resposta preflight
    }
}