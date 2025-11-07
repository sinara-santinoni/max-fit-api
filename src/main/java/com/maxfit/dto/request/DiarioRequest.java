package com.maxfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiarioRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotBlank(message = "Treino executado é obrigatório")
    private String treinoExecutado;

    private Integer avaliacao;

    private String objetivo;

    private String feitoHoje;

    private String comoMeSenti;

    private String imagem;
}
