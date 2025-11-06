package com.maxfit.model.dto.response;

import com.maxfit.model.StatusDesafio;
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
