package com.maxfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreinoResponse {

    private Long id;

    private Long alunoId;      // 🔥 ADICIONAR
    private Long personalId;   // 🔥 ADICIONAR

    private String titulo;
    private String objetivo;
    private String nivel;

    private LocalDate validade;

    private LocalDateTime criadoEm; // 🔥 ADICIONAR — aparece no front

    private List<ExercicioResponse> exercicios;
}
