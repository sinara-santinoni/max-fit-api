package com.maxfit.dto.response;

import com.maxfit.StatusDesafio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesafioResponse {
    private Long id;
    private Long alunoId;
    private String nomeAluno;
    private String titulo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusDesafio status;
}
