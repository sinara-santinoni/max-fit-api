package com.maxfit.Controllers;

import com.maxfit.dto.request.ProgressoRequest;
import com.maxfit.dto.request.ProgressoUpdateRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.ProgressoResponse;
import com.maxfit.services.ProgressoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progresso")
@RequiredArgsConstructor
public class ProgressoController {

    private final ProgressoService progressoService;

    // ====== LISTAR PROGRESSO POR ALUNO ======
    @GetMapping("/{alunoId}")
    public ResponseEntity<ApiResponse<List<ProgressoResponse>>> listarProgresso(@PathVariable Long alunoId) {
        try {
            List<ProgressoResponse> progresso = progressoService.buscarProgresso(alunoId);
            return ResponseEntity.ok(
                    ApiResponse.success("Lista de progresso retornada com sucesso!", progresso)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Erro ao listar progresso: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erro interno do servidor."));
        }
    }

    // ====== CADASTRAR NOVO PROGRESSO ======
    @PostMapping
    public ResponseEntity<ApiResponse<ProgressoResponse>> cadastrarProgresso(
            @Valid @RequestBody ProgressoRequest request) {
        try {
            ProgressoResponse response = progressoService.cadastrarProgresso(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Progresso registrado com sucesso!", response));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Erro ao registrar progresso: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erro interno do servidor."));
        }
    }

    // ====== ATUALIZAR PROGRESSO EXISTENTE ======
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> atualizarProgresso(
            @PathVariable Long id,
            @Valid @RequestBody ProgressoUpdateRequest request) {
        try {
            progressoService.atualizarProgresso(id, request);
            return ResponseEntity.ok(ApiResponse.success("Progresso atualizado com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Erro ao atualizar progresso: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erro interno do servidor."));
        }
    }
}
