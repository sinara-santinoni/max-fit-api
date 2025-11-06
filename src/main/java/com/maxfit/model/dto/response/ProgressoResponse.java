package com.maxfit.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressoResponse {
    private Long id;
    private LocalDate dataRegistro;
    private String exercicio;
    private Double peso;
    private Integer repeticoes;
    private Integer series;
    private Integer rpe;
    private String observacoes;
}
