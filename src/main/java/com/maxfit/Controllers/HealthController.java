package com.maxfit.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String home() {
        return "✅ API MaxFit rodando (Spring Boot). Verificando conexão com banco em /test-db.";
    }

    @GetMapping("/test-db")
    public Map<String, Object> testDb() {
        Map<String, Object> response = new HashMap<>();

        try {
            LocalDateTime now = jdbcTemplate.queryForObject("SELECT NOW()", LocalDateTime.class);
            response.put("status", "✅ Banco conectado com sucesso!");
            response.put("horaServidor", now);
        } catch (Exception e) {
            response.put("status", "❌ Falha ao conectar");
            response.put("erro", e.getMessage());
        }

        return response;
    }
}
