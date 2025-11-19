package com.maxfit.dto.request;

import com.maxfit.model.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CadastroRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotNull(message = "Tipo de usuário é obrigatório")
    private TipoUsuario tipo;

    // 👇 NOVO CAMPO
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;
}
