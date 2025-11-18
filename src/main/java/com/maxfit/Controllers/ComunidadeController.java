package com.maxfit.Controllers;

import com.maxfit.dto.request.PostagemRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.PostagemResponse;
import com.maxfit.services.ComunidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunidade")
@RequiredArgsConstructor
public class ComunidadeController {

    private final ComunidadeService comunidadeService;

    @GetMapping
    public ResponseEntity<List<PostagemResponse>> listar() {
        return ResponseEntity.ok(comunidadeService.listarPostagens());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostagemResponse>> criar(
            @Valid @RequestBody PostagemRequest request
    ) {
        try {
            PostagemResponse criada = comunidadeService.criarPostagem(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Postagem criada com sucesso!", criada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
