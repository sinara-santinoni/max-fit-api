package com.maxfit.Controllers;

import com.maxfit.dto.request.DesafioRequest;
import com.maxfit.dto.request.ParticiparDesafioRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.DesafioResponse;
import com.maxfit.services.DesafioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/desafios")
@RequiredArgsConstructor
public class DesafioController {

    private final DesafioService desafioService;

    @GetMapping
    public ResponseEntity<List<DesafioResponse>> listarTodosDesafios() {
        List<DesafioResponse> desafios = desafioService.listarTodosDesafios();
        return ResponseEntity.ok(desafios);
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity<List<DesafioResponse>> listarDesafiosDoAluno(@PathVariable Long alunoId) {
        List<DesafioResponse> desafios = desafioService.listarDesafiosDoAluno(alunoId);
        return ResponseEntity.ok(desafios);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> criarDesafio(@Valid @RequestBody DesafioRequest request) {
        try {
            desafioService.criarDesafio(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Desafio criado com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<ApiResponse<Void>> concluirDesafio(@PathVariable Long id) {
        try {
            desafioService.concluirDesafio(id);
            return ResponseEntity.ok(ApiResponse.success("Desafio concluído com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> excluirDesafio(@PathVariable Long id) {
        try {
            desafioService.excluirDesafio(id);
            return ResponseEntity.ok(ApiResponse.success("Desafio excluído com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{id}/participar")
    public ResponseEntity<ApiResponse<Void>> participarDesafio(
            @PathVariable Long id,
            @Valid @RequestBody ParticiparDesafioRequest request) {
        try {
            desafioService.participarDesafio(id, request);
            return ResponseEntity.ok(ApiResponse.success("Participação registrada com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
