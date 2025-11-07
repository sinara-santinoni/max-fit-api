package com.maxfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiarioResponse {
    private Long id;
    private Long alunoId;
    private LocalDate data;
    private String treinoExecutado;
    private Integer avaliacao;
    private String objetivo;
    private String feitoHoje;
    private String comoMeSenti;
    private String imagem;
}
