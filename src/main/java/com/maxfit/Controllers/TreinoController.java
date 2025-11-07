package com.maxfit.Controllers;

import com.maxfit.dto.request.TreinoRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.TreinoResponse;
import com.maxfit.services.TreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treinos")
@RequiredArgsConstructor
public class TreinoController {

    private final TreinoService treinoService;

    // POST /api/treinos  -> cria um treino
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> cadastrarTreino(@Valid @RequestBody TreinoRequest request) {
        try {
            treinoService.cadastrarTreino(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Treino cadastrado com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // GET /api/treinos -> lista todos os treinos
    @GetMapping
    public ResponseEntity<ApiResponse<List<TreinoResponse>>> listarTodos() {
        try {
            List<TreinoResponse> lista = treinoService.listarTodos();
            return ResponseEntity.ok(
                    ApiResponse.success("Lista de treinos retornada com sucesso.", lista)
            );
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ApiResponse.error("Erro interno do servidor: " + e.getMessage()));
        }
    }

    // GET /api/treinos/{alunoId} -> lista treinos de um aluno específico
    @GetMapping("/{alunoId}")
    public ResponseEntity<List<TreinoResponse>> buscarTreinosDoAluno(@PathVariable Long alunoId) {
        List<TreinoResponse> treinos = treinoService.buscarTreinosDoAluno(alunoId);
        return ResponseEntity.ok(treinos);
    }
}
