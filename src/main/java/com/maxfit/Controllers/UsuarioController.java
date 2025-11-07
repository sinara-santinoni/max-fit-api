package com.maxfit.Controllers;

import com.maxfit.dto.request.CadastroRequest;
import com.maxfit.dto.request.LoginRequest;
import com.maxfit.dto.request.VincularAlunoRequest;
import com.maxfit.dto.response.AlunoResponse;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.CadastroResponse;
import com.maxfit.dto.response.LoginResponse;
import com.maxfit.model.Usuario;
import com.maxfit.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ===== CADASTRAR USUÁRIO =====
    @PostMapping("/cadastro")
    public ResponseEntity<CadastroResponse> cadastrar(@Valid @RequestBody CadastroRequest request) {
        CadastroResponse response = usuarioService.cadastrar(request);
        HttpStatus status = response.isSucesso() ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(response);
    }

    // ===== LOGIN =====
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = usuarioService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // ===== LISTAR TODOS OS USUÁRIOS =====
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarTodos();
            return ResponseEntity.ok(Map.of(
                    "sucesso", true,
                    "mensagem", "Lista de usuários retornada com sucesso.",
                    "data", usuarios
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "sucesso", false,
                    "mensagem", "Erro interno ao listar usuários: " + e.getMessage(),
                    "data", null
            ));
        }
    }

    // ===== ALUNOS DISPONÍVEIS =====
    @GetMapping("/alunos-disponiveis")
    public ResponseEntity<List<AlunoResponse>> buscarAlunosDisponiveis() {
        List<AlunoResponse> alunos = usuarioService.buscarAlunosDisponiveis();
        return ResponseEntity.ok(alunos);
    }

    // ===== ALUNOS DE UM PERSONAL =====
    @GetMapping("/alunos-do-personal/{idPersonal}")
    public ResponseEntity<List<AlunoResponse>> buscarAlunosDoPersonal(@PathVariable Long idPersonal) {
        List<AlunoResponse> alunos = usuarioService.buscarAlunosDoPersonal(idPersonal);
        return ResponseEntity.ok(alunos);
    }

    // ===== VINCULAR ALUNO =====
    @PutMapping("/vincular-aluno")
    public ResponseEntity<ApiResponse<Void>> vincularAluno(@Valid @RequestBody VincularAlunoRequest request) {
        try {
            usuarioService.vincularAluno(request);
            return ResponseEntity.ok(ApiResponse.success("Aluno vinculado com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // ===== REMOVER ALUNO =====
    @PutMapping("/remover-aluno/{idAluno}")
    public ResponseEntity<ApiResponse<Void>> removerAluno(@PathVariable Long idAluno) {
        try {
            usuarioService.removerAluno(idAluno);
            return ResponseEntity.ok(ApiResponse.success("Aluno desvinculado com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
