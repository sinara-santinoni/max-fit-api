package com.maxfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExercicioRequest {

    @NotBlank(message = "Nome do exercício é obrigatório")
    private String nome;

    private Integer series;

    private Integer repeticoes;

    private Integer descanso;

    private String observacoes;
}
