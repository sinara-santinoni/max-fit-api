package com.maxfit.dto.response;

import com.maxfit.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipo;
    private Long personalId;
}
