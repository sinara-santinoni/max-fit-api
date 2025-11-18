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

    // LISTAR TODOS OS DESAFIOS
    @GetMapping
    public ResponseEntity<List<DesafioResponse>> listarTodosDesafios() {
        return ResponseEntity.ok(desafioService.listarTodosDesafios());
    }

    // LISTAR DESAFIOS DO ALUNO (meus desafios)
    @GetMapping("/{alunoId}")
    public ResponseEntity<List<DesafioResponse>> listarDesafiosDoAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(desafioService.listarDesafiosDoAluno(alunoId));
    }

    // CRIAR DESAFIO
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> criarDesafio(
            @Valid @RequestBody DesafioRequest request) {
        try {
            desafioService.criarDesafio(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Desafio criado com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // CONCLUIR DESAFIO (recebe id do desafio e id do aluno)
    @PostMapping("/{id}/concluir")
    public ResponseEntity<ApiResponse<Void>> concluirDesafio(
            @PathVariable Long id,
            @RequestParam Long alunoId
    ) {
        try {
            desafioService.concluirDesafio(id, alunoId);
            return ResponseEntity.ok(
                    ApiResponse.success("Desafio concluído com sucesso!")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // EXCLUIR DESAFIO
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> excluirDesafio(@PathVariable Long id) {
        try {
            desafioService.excluirDesafio(id);
            return ResponseEntity.ok(
                    ApiResponse.success("Desafio excluído com sucesso!")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // PARTICIPAR DE DESAFIO
    @PostMapping("/{id}/participar")
    public ResponseEntity<ApiResponse<Void>> participarDesafio(
            @PathVariable Long id,
            @Valid @RequestBody ParticiparDesafioRequest request
    ) {
        try {
            desafioService.participarDesafio(id, request);
            return ResponseEntity.ok(
                    ApiResponse.success("Participação registrada com sucesso!")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
