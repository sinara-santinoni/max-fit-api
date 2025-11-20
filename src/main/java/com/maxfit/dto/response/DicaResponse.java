package com.maxfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DicaResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private String categoria;
    private String conteudo;
}
