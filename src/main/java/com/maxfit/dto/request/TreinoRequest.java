package com.maxfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreinoRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @NotNull(message = "ID do personal é obrigatório")
    private Long personalId;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String objetivo;

    private String nivel;

    private LocalDate validade;

    @NotEmpty(message = "Lista de exercícios não pode estar vazia")
    private List<ExercicioRequest> exercicios;
}