package com.maxfit.model.Controllers;

import com.maxfit.dto.request.ProgressoRequest;
import com.maxfit.dto.request.ProgressoUpdateRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.ProgressoResponse;
import com.maxfit.service.ProgressoService;
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

    @GetMapping("/{alunoId}")
    public ResponseEntity<List<ProgressoResponse>> buscarProgresso(@PathVariable Long alunoId) {
        List<ProgressoResponse> progresso = progressoService.buscarProgresso(alunoId);
        return ResponseEntity.ok(progresso);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProgressoResponse>> cadastrarProgresso(
            @Valid @RequestBody ProgressoRequest request) {
        try {
            ProgressoResponse response = progressoService.cadastrarProgresso(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Progresso salvo com sucesso!", response));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

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
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}