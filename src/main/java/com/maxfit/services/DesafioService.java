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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final UsuarioRepository usuarioRepository;
    private final DesafioAlunoRepository desafioAlunoRepository;

    // ===== LISTAR TODOS OS DESAFIOS (otimizado) =====
    public List<DesafioResponse> listarTodosDesafios() {
        log.info("Listando todos os desafios");

        List<Desafio> desafios = desafioRepository.findAll();

        if (desafios.isEmpty()) {
            return List.of();
        }

        // Agrupa IDs para buscar participações em lote (evita N+1)
        List<Long> desafioIds = desafios.stream()
                .map(Desafio::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<DesafioAluno> participacoes = desafioAlunoRepository.findByDesafioIdIn(desafioIds);

        // Map de desafioId -> lista de participações
        Map<Long, List<DesafioAluno>> mapaParticipacoes = participacoes.stream()
                .collect(Collectors.groupingBy(DesafioAluno::getDesafioId));

        // Monta response usando o mapa para contar participantes e pegar comentários/participantes se necessário
        return desafios.stream()
                .map(d -> toResponse(d, mapaParticipacoes.getOrDefault(d.getId(), List.of())))
                .collect(Collectors.toList());
    }

    // ===== LISTAR DESAFIOS DO ALUNO (criados + participando) =====
    public List<DesafioResponse> listarDesafiosDoAluno(Long alunoId) {
        log.info("Listando desafios do aluno ID: {}", alunoId);

        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        // Desafios criados pelo aluno (em lote)
        List<Desafio> criados = desafioRepository.findByAlunoId(alunoId);

        // Participações do aluno (somente para obter os IDs dos desafios que participa)
        List<DesafioAluno> participacoesDoAluno = desafioAlunoRepository.findByAlunoId(alunoId);

        List<Long> idsParticipando = participacoesDoAluno.stream()
                .map(DesafioAluno::getDesafioId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Desafio> participando = idsParticipando.isEmpty()
                ? List.of()
                : desafioRepository.findAllById(idsParticipando);

        // Combina e remove duplicatas
        Map<Long, Desafio> combinadosMap = new LinkedHashMap<>();
        for (Desafio d : criados) combinadosMap.put(d.getId(), d);
        for (Desafio d : participando) combinadosMap.putIfAbsent(d.getId(), d);

        List<Desafio> combinados = new ArrayList<>(combinadosMap.values());

        // Buscar participações em lote para essa lista de desafios
        List<Long> combinadosIds = combinados.stream().map(Desafio::getId).collect(Collectors.toList());
        List<DesafioAluno> participacoes = combinadosIds.isEmpty()
                ? List.of()
                : desafioAlunoRepository.findByDesafioIdIn(combinadosIds);

        Map<Long, List<DesafioAluno>> mapaParticipacoes = participacoes.stream()
                .collect(Collectors.groupingBy(DesafioAluno::getDesafioId));

        return combinados.stream()
                .map(d -> toResponse(d, mapaParticipacoes.getOrDefault(d.getId(), List.of())))
                .collect(Collectors.toList());
    }

    // ===== LISTAR PARTICIPANTES DE UM DESAFIO =====
    public List<ParticipanteResponse> listarParticipantes(Long desafioId) {
        log.info("Listando participantes do desafio ID: {}", desafioId);

        Desafio desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Desafio não encontrado"));

        List<DesafioAluno> participacoes = desafioAlunoRepository.findByDesafioId(desafioId);

        return participacoes.stream()
                .map(pa -> {
                    Usuario aluno = usuarioRepository.findById(pa.getAlunoId()).orElse(null);
                    if (aluno == null) return null;
                    return ParticipanteResponse.builder()
                            .id(pa.getId())
                            .alunoId(aluno.getId())
                            .alunoNome(aluno.getNome())
                            .alunoEmail(aluno.getEmail())
                            .dataParticipacao(pa.getDataParticipacao())
                            .status("PARTICIPANDO")
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ===== CRIAR DESAFIO =====
    @Transactional
    public DesafioResponse criarDesafio(DesafioRequest request) {
        log.info("Criando desafio para aluno ID: {}", request.getAlunoId());

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        // Validações simples
        if (request.getMeta() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meta inválida (deve ser > 0)");
        }
        if (request.getDataInicio() == null || request.getDataFim() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de início e fim são obrigatórias");
        }
        if (request.getDataInicio().isAfter(request.getDataFim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de início não pode ser após a data de fim");
        }

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

        // Retorna response (sem executar queries adicionais)
        return toResponse(salvo, List.of(participacao));
    }

    // ===== CONCLUIR DESAFIO =====
    @Transactional
    public void concluirDesafio(Long desafioId, Long alunoId) {
        log.info("Concluir desafio {} solicitado por aluno {}", desafioId, alunoId);

        Desafio desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Desafio não encontrado"));

        // Só o criador pode concluir
        if (!desafio.getAluno().getId().equals(alunoId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas o criador pode concluir o desafio");
        }

        if (desafio.getStatus() == StatusDesafio.CONCLUIDO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desafio já está concluído");
        }

        desafio.setStatus(StatusDesafio.CONCLUIDO);
        desafio.setProgressoAtual(100.0);
        desafio.setDataConclusao(LocalDateTime.now());

        desafioRepository.save(desafio);
    }

    // ===== EXCLUIR DESAFIO =====
    @Transactional
    public void excluirDesafio(Long id, Long solicitanteId) {
        log.info("Excluir desafio {} solicitado por {}", id, solicitanteId);

        Desafio desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Desafio não encontrado"));

        // Só o criador pode excluir
        if (!desafio.getAluno().getId().equals(solicitanteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas o criador pode excluir este desafio");
        }

        // Remover participações em lote (se houver)
        List<DesafioAluno> participacoes = desafioAlunoRepository.findByDesafioId(id);
        if (!participacoes.isEmpty()) {
            desafioAlunoRepository.deleteAll(participacoes);
        }

        // Remover desafio
        desafioRepository.deleteById(id);
    }

    // ===== PARTICIPAR DO DESAFIO =====
    @Transactional
    public void participarDesafio(Long id, ParticiparDesafioRequest request) {
        log.info("Aluno {} quer participar do desafio {}", request.getAlunoId(), id);

        Desafio desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Desafio não encontrado"));

        if (desafio.getStatus() != StatusDesafio.ATIVO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desafio não está ativo");
        }

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        // Já participa?
        boolean jaParticipa = desafioAlunoRepository.existsByDesafioIdAndAlunoId(id, request.getAlunoId());
        if (jaParticipa) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Você já está participando deste desafio");
        }

        // Criar participação — NÃO ALTERAR desafio.progressoAtual aqui (progresso deve ser individual)
        DesafioAluno participacao = DesafioAluno.builder()
                .desafioId(id)
                .alunoId(aluno.getId())
                .dataParticipacao(LocalDate.now())
                .build();

        desafioAlunoRepository.save(participacao);

        // Observação: se você quiser dividir progresso por participante, crie uma entidade separada para o progresso do participante.
    }

    // ===== SAIR DO DESAFIO =====
    @Transactional
    public void sairDesafio(Long desafioId, Long alunoId) {
        log.info("Aluno {} saindo do desafio {}", alunoId, desafioId);

        DesafioAluno participacao = desafioAlunoRepository
                .findByDesafioIdAndAlunoId(desafioId, alunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não está participando deste desafio"));

        desafioAlunoRepository.delete(participacao);
    }

    // ===== HELPERS / MAPPERS =====
    private DesafioResponse toResponse(Desafio d, List<DesafioAluno> participacoes) {
        int totalParticipantes = participacoes == null ? 0 : participacoes.size();

        return DesafioResponse.builder()
                .id(d.getId())
                .alunoId(d.getAluno().getId())
                .alunoNome(d.getAluno().getNome())
                .titulo(d.getTitulo())
                .descricao(d.getDescricao())
                .meta(d.getMeta())
                .dataInicio(d.getDataInicio())
                .dataFim(d.getDataFim())
                .status(d.getStatus())
                .progressoAtual(d.getProgressoAtual())
                .dataCriacao(d.getDataCriacao())
                .dataConclusao(d.getDataConclusao())
                .totalParticipantes(totalParticipantes)
                .build();
    }
}
