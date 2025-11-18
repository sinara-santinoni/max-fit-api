package com.maxfit.services;

import com.maxfit.dto.request.DesafioRequest;
import com.maxfit.dto.request.ParticiparDesafioRequest;
import com.maxfit.dto.response.DesafioResponse;
import com.maxfit.dto.response.ParticipanteResponse;
import com.maxfit.model.Desafio;
import com.maxfit.model.DesafioAluno;
import com.maxfit.model.StatusDesafio;
import com.maxfit.model.Usuario;
import com.maxfit.repository.DesafioAlunoRepository;
import com.maxfit.repository.DesafioRepository;
import com.maxfit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final UsuarioRepository usuarioRepository;
    private final DesafioAlunoRepository desafioAlunoRepository;

    // LISTAR TODOS OS DESAFIOS
    public List<DesafioResponse> listarTodosDesafios() {
        return desafioRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // LISTAR DESAFIOS DO ALUNO
    public List<DesafioResponse> listarDesafiosDoAluno(Long alunoId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Buscar desafios criados pelo aluno
        List<Desafio> desafiosCriados = desafioRepository.findByAluno(aluno);

        // Buscar desafios que o aluno está participando
        List<DesafioAluno> participacoes = desafioAlunoRepository.findByAlunoId(alunoId);
        List<Desafio> desafiosParticipando = participacoes.stream()
                .map(da -> desafioRepository.findById(da.getDesafioId())
                        .orElse(null))
                .filter(d -> d != null)
                .collect(Collectors.toList());

        // Combinar ambos (sem duplicatas)
        desafiosCriados.addAll(desafiosParticipando);

        return desafiosCriados.stream()
                .distinct()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 🆕 LISTAR PARTICIPANTES DE UM DESAFIO
    public List<ParticipanteResponse> listarParticipantes(Long desafioId) {
        log.info("Listando participantes do desafio ID: {}", desafioId);

        // Verificar se o desafio existe
        Desafio desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        // Buscar todos os participantes
        List<DesafioAluno> participacoes = desafioAlunoRepository.findByDesafioId(desafioId);

        return participacoes.stream()
                .map(da -> {
                    Usuario aluno = usuarioRepository.findById(da.getAlunoId())
                            .orElse(null);

                    if (aluno == null) return null;

                    return ParticipanteResponse.builder()
                            .id(da.getId())
                            .alunoId(aluno.getId())
                            .alunoNome(aluno.getNome())
                            .alunoEmail(aluno.getEmail())
                            .dataParticipacao(da.getDataParticipacao())
                            .progressoAtual(desafio.getProgressoAtual())
                            .status("PARTICIPANDO")
                            .build();
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }

    // CRIAR DESAFIO
    @Transactional
    public DesafioResponse criarDesafio(DesafioRequest request) {
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

        Desafio salvo = desafioRepository.save(desafio);

        // Criar participação automática do criador
        DesafioAluno participacao = DesafioAluno.builder()
                .desafioId(salvo.getId())
                .alunoId(aluno.getId())
                .dataParticipacao(LocalDate.now())
                .build();

        desafioAlunoRepository.save(participacao);

        return convertToResponse(salvo);
    }

    // CONCLUIR DESAFIO (RECEBE alunoId)
    @Transactional
    public void concluirDesafio(Long desafioId, Long alunoId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Desafio desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        // Garantir que o aluno seja o dono do desafio
        if (!desafio.getAluno().getId().equals(aluno.getId())) {
            throw new RuntimeException("Você não pode concluir um desafio que não é seu.");
        }

        if (desafio.getStatus() == StatusDesafio.CONCLUIDO) {
            throw new RuntimeException("Desafio já está concluído");
        }

        desafio.setStatus(StatusDesafio.CONCLUIDO);
        desafio.setProgressoAtual(100.0);
        desafio.setDataConclusao(LocalDateTime.now());

        desafioRepository.save(desafio);
    }

    // EXCLUIR DESAFIO
    @Transactional
    public void excluirDesafio(Long id) {
        if (!desafioRepository.existsById(id)) {
            throw new RuntimeException("Desafio não encontrado");
        }

        // Remover todas as participações primeiro
        List<DesafioAluno> participacoes = desafioAlunoRepository.findByDesafioId(id);
        desafioAlunoRepository.deleteAll(participacoes);

        // Remover o desafio
        desafioRepository.deleteById(id);
    }

    // PARTICIPAR DO DESAFIO
    @Transactional
    public void participarDesafio(Long id, ParticiparDesafioRequest request) {
        Desafio desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (desafio.getStatus() != StatusDesafio.ATIVO) {
            throw new RuntimeException("Desafio não está ativo");
        }

        // Verificar se já está participando
        boolean jaParticipa = desafioAlunoRepository
                .existsByDesafioIdAndAlunoId(id, request.getAlunoId());

        if (jaParticipa) {
            throw new RuntimeException("Você já está participando deste desafio");
        }

        // Criar participação
        DesafioAluno participacao = DesafioAluno.builder()
                .desafioId(id)
                .alunoId(aluno.getId())
                .dataParticipacao(LocalDate.now())
                .build();

        desafioAlunoRepository.save(participacao);

        // Atualizar progresso se fornecido
        if (request.getProgressoAtual() != null) {
            desafio.setProgressoAtual(request.getProgressoAtual());

            if (request.getProgressoAtual() >= 100.0) {
                desafio.setStatus(StatusDesafio.CONCLUIDO);
                desafio.setDataConclusao(LocalDateTime.now());
            }

            desafioRepository.save(desafio);
        }
    }

    // 🆕 SAIR DE UM DESAFIO
    @Transactional
    public void sairDesafio(Long desafioId, Long alunoId) {
        log.info("Aluno {} saindo do desafio {}", alunoId, desafioId);

        DesafioAluno participacao = desafioAlunoRepository
                .findByDesafioIdAndAlunoId(desafioId, alunoId)
                .orElseThrow(() -> new RuntimeException("Você não está participando deste desafio"));

        desafioAlunoRepository.delete(participacao);
    }

    // CONVERTE ENTIDADE PARA RESPONSE
    private DesafioResponse convertToResponse(Desafio desafio) {
        // Contar participantes
        int totalParticipantes = desafioAlunoRepository
                .findByDesafioId(desafio.getId())
                .size();

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
                .totalParticipantes(totalParticipantes)
                .build();
    }
}