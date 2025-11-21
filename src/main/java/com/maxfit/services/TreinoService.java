package com.maxfit.services;

import com.maxfit.dto.request.TreinoRequest;
import com.maxfit.dto.response.ExercicioResponse;
import com.maxfit.dto.response.TreinoResponse;
import com.maxfit.model.Exercicio;
import com.maxfit.model.Treino;
import com.maxfit.repository.TreinoRepository;

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
public class TreinoService {

    private final TreinoRepository treinoRepository;

    @Transactional
    public void cadastrarTreino(TreinoRequest request) {
        log.info("Cadastrando treino: {} para aluno {}", request.getTitulo(), request.getAlunoId());

        Treino treino = new Treino();
        treino.setAlunoId(request.getAlunoId());
        treino.setPersonalId(request.getPersonalId());
        treino.setTitulo(request.getTitulo());
        treino.setObjetivo(request.getObjetivo());
        treino.setNivel(request.getNivel());
        treino.setValidade(request.getValidade());

        // 🔥 ESSA É A ÚNICA COISA NOVA
        treino.setCriadoEm(LocalDateTime.now());

        // Exercícios
        request.getExercicios().forEach(exReq -> {
            Exercicio exercicio = new Exercicio();
            exercicio.setNome(exReq.getNome());
            exercicio.setSeries(exReq.getSeries());
            exercicio.setRepeticoes(exReq.getRepeticoes());
            exercicio.setDescanso(exReq.getDescanso());
            exercicio.setObservacoes(exReq.getObservacoes());
            treino.addExercicio(exercicio);
        });

        Treino salvo = treinoRepository.save(treino);
        log.info("Novo treino criado: {} (ID {}) para aluno {}", salvo.getTitulo(), salvo.getId(), salvo.getAlunoId());
    }

    public List<TreinoResponse> listarTodos() {
        List<Treino> treinos = treinoRepository.findAll();

        return treinos.stream()
                .map(this::toTreinoResponse)
                .collect(Collectors.toList());
    }

    public List<TreinoResponse> buscarTreinosDoAluno(Long alunoId) {
        List<Treino> treinos = treinoRepository.findByAlunoIdOrderByIdDesc(alunoId);

        return treinos.stream()
                .map(this::toTreinoResponse)
                .collect(Collectors.toList());
    }

    private TreinoResponse toTreinoResponse(Treino treino) {
        List<ExercicioResponse> exercicios = treino.getExercicios().stream()
                .map(this::toExercicioResponse)
                .collect(Collectors.toList());

        return TreinoResponse.builder()
                .id(treino.getId())
                .alunoId(treino.getAlunoId())
                .personalId(treino.getPersonalId())
                .titulo(treino.getTitulo())
                .objetivo(treino.getObjetivo())
                .nivel(treino.getNivel())
                .validade(treino.getValidade())
                .criadoEm(treino.getCriadoEm())
                .exercicios(exercicios)
                .build();
    }

    private ExercicioResponse toExercicioResponse(Exercicio exercicio) {
        return ExercicioResponse.builder()
                .nome(exercicio.getNome())
                .series(exercicio.getSeries())
                .repeticoes(exercicio.getRepeticoes())
                .descanso(exercicio.getDescanso())
                .observacoes(exercicio.getObservacoes())
                .build();
    }

    // ⭐ NOVO — Buscar treino pelo ID (usado ao clicar "Ver detalhes")
    public TreinoResponse buscarPorId(Long treinoId) {
        Treino treino = treinoRepository.findById(treinoId)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado!"));

        return toTreinoResponse(treino);
    }
}
