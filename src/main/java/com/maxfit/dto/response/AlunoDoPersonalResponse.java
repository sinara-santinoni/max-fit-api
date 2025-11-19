package com.maxfit.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AlunoDoPersonalResponse {

    private Long id;
    private String nome;
    private String email;
    private String cidade;
    private Long personalId;
    private LocalDateTime criadoEm;
}
