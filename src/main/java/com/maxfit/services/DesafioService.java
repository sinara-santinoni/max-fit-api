package com.maxfit.services;

import com.maxfit.dto.request.DesafioRequest;
import com.maxfit.dto.request.ParticiparDesafioRequest;
import com.maxfit.dto.response.DesafioResponse;
import com.maxfit.model.Desafio;
import com.maxfit.model.Usuario;
import com.maxfit.model.StatusDesafio;
import com.maxfit.repository.DesafioRepository;
import com.maxfit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final UsuarioRepository usuarioRepository;

    public List<DesafioResponse> listarTodosDesafios() {
        return desafioRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<DesafioResponse> listarDesafiosDoAluno(Long alunoId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return desafioRepository.findByAluno(aluno).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void criarDesafio(DesafioRequest request) {
        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Desafio desafio = Desafio.builder()
                .aluno(aluno)
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .meta(request.getMeta())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .status(StatusDesafio.ATIVO)
                .progressoAtual(0.0)
                .dataCriacao(LocalDateTime.now())
                .build();

        desafioRepository.save(desafio);
    }

    @Transactional
    public void concluirDesafio(Long id) {
        Desafio desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        if (desafio.getStatus() == StatusDesafio.CONCLUIDO) {
            throw new RuntimeException("Desafio já está concluído");
        }

        desafio.setStatus(StatusDesafio.CONCLUIDO);
        desafio.setProgressoAtual(100.0);
        desafio.setDataConclusao(LocalDateTime.now());

        desafioRepository.save(desafio);
    }

    @Transactional
    public void excluirDesafio(Long id) {
        if (!desafioRepository.existsById(id)) {
            throw new RuntimeException("Desafio não encontrado");
        }
        desafioRepository.deleteById(id);
    }

    @Transactional
    public void participarDesafio(Long id, ParticiparDesafioRequest request) {
        Desafio desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (desafio.getStatus() != StatusDesafio.ATIVO) {
            throw new RuntimeException("Desafio não está ativo para participação");
        }

        // Aqui você pode adicionar lógica adicional para registrar a participação
        // Por exemplo, adicionar o aluno à lista de participantes do desafio
        // ou criar uma entidade separada para gerenciar participações

        // Atualizar progresso se fornecido
        if (request.getProgressoAtual() != null) {
            desafio.setProgressoAtual(request.getProgressoAtual());

            // Auto-concluir se atingiu 100%
            if (request.getProgressoAtual() >= 100.0) {
                desafio.setStatus(StatusDesafio.CONCLUIDO);
                desafio.setDataConclusao(LocalDateTime.now());
            }
        }

        desafioRepository.save(desafio);
    }

    private DesafioResponse convertToResponse(Desafio desafio) {
        return DesafioResponse.builder()
                .id(desafio.getId())
                .alunoId(desafio.getAluno().getId())
                .alunoNome(desafio.getAluno().getNome())
                .titulo(desafio.getTitulo())
                .descricao(desafio.getDescricao())
                .meta(desafio.getMeta())
                .dataInicio(desafio.getDataInicio())
                .dataFim(desafio.getDataFim())
                .status(desafio.getStatus())
                .progressoAtual(desafio.getProgressoAtual())
                .dataCriacao(desafio.getDataCriacao())
                .dataConclusao(desafio.getDataConclusao())
                .build();
    }
}