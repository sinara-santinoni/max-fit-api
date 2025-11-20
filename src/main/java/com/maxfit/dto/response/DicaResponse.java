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

    /**
     * Pequena descrição resumida da dica.
     * Usada na listagem do front.
     */
    private String descricao;

    /**
     * Categoria da dica:
     * - Saúde
     * - Nutrição
     * - Segurança
     * - Performance
     */
    private String categoria;

    /**
     * Conteúdo completo e detalhado da dica.
     * Usado na tela interna quando o usuário abre a dica.
     */
    private String conteudo;
}
