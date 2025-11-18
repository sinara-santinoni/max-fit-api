package com.maxfit.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ComentarioResponse {

    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private String texto;
    private LocalDateTime dataCriacao;
}