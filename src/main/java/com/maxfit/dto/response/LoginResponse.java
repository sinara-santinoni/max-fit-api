package com.maxfit.dto.response;

import com.maxfit.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipo;
}
