package com.maxfit.services;

import com.maxfit.dto.request.ComentarioRequest;
import com.maxfit.dto.request.PostagemRequest;
import com.maxfit.dto.response.ComentarioResponse;
import com.maxfit.dto.response.PostagemResponse;
import com.maxfit.model.ComentarioComunidade;
import com.maxfit.model.CurtidaComunidade;
import com.maxfit.model.PostagemComunidade;
import com.maxfit.model.Usuario;
import com.maxfit.repository.ComentarioComunidadeRepository;
import com.maxfit.repository.CurtidaComunidadeRepository;
import com.maxfit.repository.PostagemComunidadeRepository;
import com.maxfit.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComunidadeService {

    private final PostagemComunidadeRepository postagemRepository;
    private final ComentarioComunidadeRepository comentarioRepository;
    private final CurtidaComunidadeRepository curtidaRepository;
    private final UsuarioRepository usuarioRepository;

    // ==========================================================
    // LISTAR POSTAGENS
    // ==========================================================
    public List<PostagemResponse> listarPostagens(Long usuarioLogadoId) {
        return postagemRepository.findAllByOrderByDataCriacaoDesc()
                .stream()
                .map(p -> toResponse(p, usuarioLogadoId))
                .collect(Collectors.toList());
    }

    // ==========================================================
    // CRIAR POSTAGEM
    // ==========================================================
    @Transactional
    public PostagemResponse criarPostagem(PostagemRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PostagemComunidade postagem = PostagemComunidade.builder()
                .usuario(usuario)
                .texto(request.getTexto())
                .dataCriacao(LocalDateTime.now())
                .build();

        PostagemComunidade salva = postagemRepository.save(postagem);
        return toResponse(salva, request.getUsuarioId());
    }

    // ==========================================================
    // CRIAR COMENTÁRIO
    // ==========================================================
    @Transactional
    public ComentarioResponse criarComentario(Long postagemId, ComentarioRequest request) {

        PostagemComunidade postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ComentarioComunidade comentario = ComentarioComunidade.builder()
                .postagem(postagem)
                .usuario(usuario)
                .texto(request.getTexto())
                .dataCriacao(LocalDateTime.now())
                .build();

        ComentarioComunidade salvo = comentarioRepository.save(comentario);

        return ComentarioResponse.builder()
                .id(salvo.getId())
                .usuarioId(salvo.getUsuario().getId())
                .usuarioNome(salvo.getUsuario().getNome())
                .texto(salvo.getTexto())
                .dataCriacao(salvo.getDataCriacao())
                .build();
    }

    // ==========================================================
    // CURTIR / DESCURTIR POSTAGEM
    // ==========================================================
    @Transactional
    public boolean toggleCurtida(Long postagemId, Long usuarioId) {
        log.info("Toggle curtida - Postagem: {}, Usuario: {}", postagemId, usuarioId);

        PostagemComunidade postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar se já curtiu (AGORA usando método correto)
        var curtidaExistente =
                curtidaRepository.findByPostagem_IdAndUsuario_Id(postagemId, usuarioId);

        if (curtidaExistente.isPresent()) {
            // Descurtir
            curtidaRepository.delete(curtidaExistente.get());
            log.info("Curtida removida");
            return false;
        }

        // Curtir
        CurtidaComunidade novaCurtida = CurtidaComunidade.builder()
                .postagem(postagem)
                .usuario(usuario)
                .dataCriacao(LocalDateTime.now())
                .build();

        curtidaRepository.save(novaCurtida);
        log.info("Curtida adicionada");
        return true;
    }

    // ==========================================================
    // LISTAR COMENTÁRIOS
    // ==========================================================
    public List<ComentarioResponse> listarComentarios(Long postagemId) {
        return comentarioRepository.findByPostagemIdOrderByDataCriacaoAsc(postagemId)
                .stream()
                .map(c -> ComentarioResponse.builder()
                        .id(c.getId())
                        .usuarioId(c.getUsuario().getId())
                        .usuarioNome(c.getUsuario().getNome())
                        .texto(c.getTexto())
                        .dataCriacao(c.getDataCriacao())
                        .build())
                .collect(Collectors.toList());
    }

    // ==========================================================
    // CONVERTER ENTIDADE → RESPONSE
    // ==========================================================
    private PostagemResponse toResponse(PostagemComunidade p, Long usuarioLogadoId) {

        int totalCurtidas = (int) curtidaRepository.countByPostagem_Id(p.getId());
        int totalComentarios = (int) comentarioRepository.countByPostagemId(p.getId());

        boolean usuarioCurtiu = false;

        if (usuarioLogadoId != null) {
            usuarioCurtiu = curtidaRepository
                    .existsByPostagem_IdAndUsuario_Id(p.getId(), usuarioLogadoId);
        }

        List<ComentarioResponse> comentarios = listarComentarios(p.getId());

        return PostagemResponse.builder()
                .id(p.getId())
                .usuarioId(p.getUsuario().getId())
                .usuarioNome(p.getUsuario().getNome())
                .texto(p.getTexto())
                .dataCriacao(p.getDataCriacao())
                .totalCurtidas(totalCurtidas)
                .totalComentarios(totalComentarios)
                .usuarioCurtiu(usuarioCurtiu)
                .comentarios(comentarios)
                .build();
    }
}
