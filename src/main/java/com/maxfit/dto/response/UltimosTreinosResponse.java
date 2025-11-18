package com.maxfit.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UltimosTreinosResponse {

    private String data;
    private String nome;
    private boolean concluido;
}
