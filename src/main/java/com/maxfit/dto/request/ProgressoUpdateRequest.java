package com.maxfit.dto.request;

import lombok.Data;

@Data
public class ProgressoUpdateRequest {

    private Double peso;
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