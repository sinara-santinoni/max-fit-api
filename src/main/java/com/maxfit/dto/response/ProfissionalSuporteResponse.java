package com.maxfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalSuporteResponse {

    private Long id;
    private String nome;
    private String especialidade;
    private String telefone;
    private String email;
    private String cidade;

    /**
     * Tipo do profissional
     * Valores possíveis:
     *  - "PSICOLOGO"
     *  - "NUTRICIONISTA"
     */
    private String tipo;

}
