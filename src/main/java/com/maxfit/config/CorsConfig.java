package com.maxfit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Permite múltiplos domínios e suporta curingas se necessário
                .allowedOriginPatterns(
                        "https://cheerful-klepon-54ef0e.netlify.app",
                        "https://maxfit-tcc.onrender.com",
                        "http://localhost:5500"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Permite todos os headers
                .allowCredentials(true) // Permite cookies e credenciais
                .maxAge(3600); // Tempo em segundos que o browser cacheia as regras CORS
    }

}