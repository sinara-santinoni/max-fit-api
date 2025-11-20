package com.maxfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorialResponse {

    private Long id;
    private String titulo;
    private String descricao;

    /**
     * URL do conteúdo do tutorial
     * Ex: link de vídeo no YouTube
     */
    private String url;

    /**
     * Campo opcional para exibir um ícone ou thumbnail
     * Pode ser um emoji (ex: "🎥") ou URL de imagem
     */
    private String thumbnail;
}
