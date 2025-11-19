package com.maxfit.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VincularAlunoRequest {

    @NotNull
    private Long personalId;

    @NotNull
    private Long alunoId;
}
