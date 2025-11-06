package dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressoUpdateRequest {

    @NotBlank(message = "Nome do exercício é obrigatório")
    private String exercicio;

    private Double peso;

    private Integer repeticoes;

    private Integer series;

    private Integer rpe;

    private String observacoes;
}
