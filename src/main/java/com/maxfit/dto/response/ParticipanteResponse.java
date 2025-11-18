package com.maxfit.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ParticipanteResponse {

    private Long id;
    private Long alunoId;
    private String alunoNome;
    private String alunoEmail;
    private LocalDate dataParticipacao;
    private Double progressoAtual;
    private String status; // "PARTICIPANDO", "CONCLUIU", "DESISTIU"
}