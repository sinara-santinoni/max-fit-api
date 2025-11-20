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
    private String url;
    private String thumbnail;
}
