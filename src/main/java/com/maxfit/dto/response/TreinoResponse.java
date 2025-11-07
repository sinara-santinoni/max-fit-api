package com.maxfit.dto.response;

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
public class TreinoResponse {
    private Long id;
    private String titulo;
    private String objetivo;
    private String nivel;
    private LocalDate validade;
    private List<ExercicioResponse> exercicios;
}
