package com.maxfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExercicioResponse {
    private String nome;
    private Integer series;
    private Integer repeticoes;
    private Integer descanso;
    private String observacoes;
}

