package com.maxfit.Controllers;

import com.maxfit.dto.request.TreinoRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.TreinoResponse;
import com.maxfit.service.TreinoService;
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

    @GetMapping("/{alunoId}")
    public ResponseEntity<List<TreinoResponse>> buscarTreinosDoAluno(@PathVariable Long alunoId) {
        List<TreinoResponse> treinos = treinoService.buscarTreinosDoAluno(alunoId);
        return ResponseEntity.ok(treinos);
    }
}