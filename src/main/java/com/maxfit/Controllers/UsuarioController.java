package com.maxfit.Controllers;

import com.maxfit.dto.request.CadastroRequest;
import com.maxfit.dto.request.LoginRequest;
import com.maxfit.dto.request.VincularAlunoRequest;
import com.maxfit.dto.response.AlunoResponse;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.CadastroResponse;
import com.maxfit.dto.response.LoginResponse;
import com.maxfit.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroResponse> cadastrar(@Valid @RequestBody CadastroRequest request) {
        CadastroResponse response = usuarioService.cadastrar(request);

        HttpStatus status = response.isSucesso() ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(response);
    }

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

    @GetMapping("/alunos-disponiveis")
    public ResponseEntity<List<AlunoResponse>> buscarAlunosDisponiveis() {
        List<AlunoResponse> alunos = usuarioService.buscarAlunosDisponiveis();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/alunos-do-personal/{idPersonal}")
    public ResponseEntity<List<AlunoResponse>> buscarAlunosDoPersonal(@PathVariable Long idPersonal) {
        List<AlunoResponse> alunos = usuarioService.buscarAlunosDoPersonal(idPersonal);
        return ResponseEntity.ok(alunos);
    }

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
