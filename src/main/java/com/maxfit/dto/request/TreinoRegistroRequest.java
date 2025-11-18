package com.maxfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreinoRegistroRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @NotBlank(message = "Nome do treino é obrigatório")
    private String nomeTreino;

    private boolean concluido;
}
