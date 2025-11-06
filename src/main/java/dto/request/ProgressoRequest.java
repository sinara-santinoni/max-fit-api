package dto.request;

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
public class ProgressoRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @NotNull(message = "Data do registro é obrigatória")
    private LocalDate dataRegistro;

    @NotBlank(message = "Nome do exercício é obrigatório")
    private String exercicio;

    private Double peso;

    private Integer repeticoes;

    private Integer series;

    private Integer rpe;

    private String observacoes;
}

