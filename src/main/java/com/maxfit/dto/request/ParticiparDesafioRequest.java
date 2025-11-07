package com.maxfit.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ParticiparDesafioRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @Min(value = 0, message = "Progresso não pode ser negativo")
    @Max(value = 100, message = "Progresso não pode ser maior que 100")
    private Double progressoAtual;
}