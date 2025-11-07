package com.maxfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DesafioRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String descricao;

    @NotBlank(message = "Meta é obrigatória")
    private String meta;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    private LocalDateTime dataFim;
}