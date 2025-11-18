package com.maxfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostagemRequest {

    @NotNull(message = "Id do usuário é obrigatório")
    private Long usuarioId;

    @NotBlank(message = "Texto é obrigatório")
    @Size(max = 500, message = "Texto deve ter no máximo 500 caracteres")
    private String texto;
}
