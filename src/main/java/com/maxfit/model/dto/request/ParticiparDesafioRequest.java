package com.maxfit.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticiparDesafioRequest {

    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;
}