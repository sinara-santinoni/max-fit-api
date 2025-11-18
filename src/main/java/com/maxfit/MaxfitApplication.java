package com.maxfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

@SpringBootApplication
public class MaxfitApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxfitApplication.class, args);
    }

    // Bean para forçar o CorsFilter a ser executado primeiro (Ordem 0)
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Use os mesmos valores que você configurou no CorsConfig.java
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList(
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
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));

        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0); // Garante a alta prioridade
        return bean;
    }
}