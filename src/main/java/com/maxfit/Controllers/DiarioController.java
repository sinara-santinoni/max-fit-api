package com.maxfit.Controllers;

import com.maxfit.dto.request.DiarioRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.DiarioResponse;
import com.maxfit.services.DiarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/diarios")
@RequiredArgsConstructor
public class DiarioController {

    private final DiarioService diarioService;


    @PostMapping
    public ResponseEntity<ApiResponse<DiarioResponse>> registrarDiario(
            @Valid @RequestBody DiarioRequest request) {
        try {
            DiarioResponse response = diarioService.registrarDiario(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Entrada registrada com sucesso!", response));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<DiarioResponse>>> listarDiarios(@RequestParam Long alunoId) {
        try {
            List<DiarioResponse> diarios = diarioService.buscarDiarios(alunoId);
            return ResponseEntity.ok(
                    ApiResponse.success("Lista de diários retornada com sucesso.", diarios)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erro ao buscar diários: " + e.getMessage()));
        }
    }
}
