package com.maxfit.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProgressoRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @Positive(message = "Peso deve ser maior que zero")
    private Double peso;

    @Positive(message = "Altura deve ser maior que zero")
    private Double altura;

    private Double circunferenciaBracos;
    private Double circunferenciaPeito;
    private Double circunferenciaCintura;
    private Double circunferenciaQuadril;
    private Double circunferenciaCoxas;
    private Double circunferenciaPanturrilhas;

    private Double percentualGordura;
    private Double massaMuscular;

    private String observacoes;
}