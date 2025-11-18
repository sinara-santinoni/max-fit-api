package com.maxfit.services;

import com.maxfit.dto.request.PostagemRequest;
import com.maxfit.dto.response.PostagemResponse;
import com.maxfit.model.PostagemComunidade;
import com.maxfit.model.Usuario;
import com.maxfit.repository.PostagemComunidadeRepository;
import com.maxfit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComunidadeService {

    private final PostagemComunidadeRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;

    public List<PostagemResponse> listarPostagens() {
        return postagemRepository.findAllByOrderByDataCriacaoDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

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
        return toResponse(salva);
    }

    private PostagemResponse toResponse(PostagemComunidade p) {
        return PostagemResponse.builder()
                .id(p.getId())
                .usuarioId(p.getUsuario().getId())
                .usuarioNome(p.getUsuario().getNome())
                .texto(p.getTexto())
                .dataCriacao(p.getDataCriacao())
                .build();
    }
}
