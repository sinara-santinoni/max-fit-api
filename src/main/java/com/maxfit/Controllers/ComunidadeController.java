package com.maxfit.Controllers;

import com.maxfit.dto.request.ComentarioRequest;
import com.maxfit.dto.request.PostagemRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.ComentarioResponse;
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

    // LISTAR POSTAGENS
    @GetMapping
    public ResponseEntity<List<PostagemResponse>> listar(
            @RequestParam(required = false) Long usuarioId
    ) {
        return ResponseEntity.ok(comunidadeService.listarPostagens(usuarioId));
    }

    // CRIAR POSTAGEM
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

    // 🆕 CRIAR COMENTÁRIO
    @PostMapping("/{postagemId}/comentarios")
    public ResponseEntity<ApiResponse<ComentarioResponse>> comentar(
            @PathVariable Long postagemId,
            @Valid @RequestBody ComentarioRequest request
    ) {
        try {
            ComentarioResponse comentario = comunidadeService.criarComentario(postagemId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Comentário adicionado!", comentario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // 🆕 CURTIR/DESCURTIR
    @PostMapping("/{postagemId}/curtir")
    public ResponseEntity<ApiResponse<Boolean>> curtir(
            @PathVariable Long postagemId,
            @RequestParam Long usuarioId
    ) {
        try {
            boolean curtiu = comunidadeService.toggleCurtida(postagemId, usuarioId);
            String mensagem = curtiu ? "Você curtiu esta postagem! ❤️" : "Curtida removida";
            return ResponseEntity.ok(
                    ApiResponse.success(mensagem, curtiu)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // 🆕 LISTAR COMENTÁRIOS
    @GetMapping("/{postagemId}/comentarios")
    public ResponseEntity<List<ComentarioResponse>> listarComentarios(
            @PathVariable Long postagemId
    ) {
        return ResponseEntity.ok(comunidadeService.listarComentarios(postagemId));
    }
}