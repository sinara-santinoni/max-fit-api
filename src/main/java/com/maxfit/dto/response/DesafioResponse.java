package com.maxfit.dto.response;

import com.maxfit.model.StatusDesafio;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DesafioResponse {

    private Long id;
    private Long alunoId;
    private String alunoNome;

    private String titulo;
    private String descricao;
    private String meta;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    private StatusDesafio status;
    private Double progressoAtual;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
}