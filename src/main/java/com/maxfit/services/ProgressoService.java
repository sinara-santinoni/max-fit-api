package com.maxfit.services;

import com.maxfit.dto.request.ProgressoRequest;
import com.maxfit.dto.request.ProgressoUpdateRequest;
import com.maxfit.dto.response.ProgressoResponse;
import com.maxfit.model.Progresso;
import com.maxfit.model.Usuario;
import com.maxfit.repository.ProgressoRepository;
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
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final UsuarioRepository usuarioRepository;

    // ===== LISTAR PROGRESSO DO ALUNO =====
    public List<ProgressoResponse> buscarProgresso(Long alunoId) {
        log.info("Buscando progresso do aluno ID: {}", alunoId);

        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));

        List<Progresso> lista = progressoRepository.findByAlunoOrderByDataRegistroDesc(aluno);
        log.info("Foram encontrados {} registros de progresso para o aluno {}", lista.size(), aluno.getNome());

        return lista.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // ===== CADASTRAR NOVO PROGRESSO =====
    @Transactional
    public ProgressoResponse cadastrarProgresso(ProgressoRequest request) {
        log.info("Cadastrando novo progresso para aluno ID: {}", request.getAlunoId());

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));

        validarDadosProgresso(request);

        Progresso progresso = Progresso.builder()
                .aluno(aluno)
                .peso(request.getPeso())
                .altura(request.getAltura())
                .imc(calcularIMC(request.getPeso(), request.getAltura()))
                .circunferenciaBracos(request.getCircunferenciaBracos())
                .circunferenciaPeito(request.getCircunferenciaPeito())
                .circunferenciaCintura(request.getCircunferenciaCintura())
                .circunferenciaQuadril(request.getCircunferenciaQuadril())
                .circunferenciaCoxas(request.getCircunferenciaCoxas())
                .circunferenciaPanturrilhas(request.getCircunferenciaPanturrilhas())
                .percentualGordura(request.getPercentualGordura())
                .massaMuscular(request.getMassaMuscular())
                .observacoes(request.getObservacoes())
                .dataRegistro(LocalDateTime.now())
                .build();

        Progresso salvo = progressoRepository.save(progresso);
        log.info("Progresso salvo com sucesso! ID: {}", salvo.getId());

        return convertToResponse(salvo);
    }

    // ===== ATUALIZAR PROGRESSO EXISTENTE =====
    @Transactional
    public void atualizarProgresso(Long id, ProgressoUpdateRequest request) {
        log.info("Atualizando progresso ID: {}", id);

        Progresso progresso = progressoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de progresso não encontrado."));

        boolean recalcularIMC = false;

        if (request.getPeso() != null) {
            progresso.setPeso(request.getPeso());
            recalcularIMC = true;
        }
        if (request.getAltura() != null) {
            progresso.setAltura(request.getAltura());
            recalcularIMC = true;
        }
        if (recalcularIMC) {
            progresso.setImc(calcularIMC(progresso.getPeso(), progresso.getAltura()));
        }

        if (request.getCircunferenciaBracos() != null) progresso.setCircunferenciaBracos(request.getCircunferenciaBracos());
        if (request.getCircunferenciaPeito() != null) progresso.setCircunferenciaPeito(request.getCircunferenciaPeito());
        if (request.getCircunferenciaCintura() != null) progresso.setCircunferenciaCintura(request.getCircunferenciaCintura());
        if (request.getCircunferenciaQuadril() != null) progresso.setCircunferenciaQuadril(request.getCircunferenciaQuadril());
        if (request.getCircunferenciaCoxas() != null) progresso.setCircunferenciaCoxas(request.getCircunferenciaCoxas());
        if (request.getCircunferenciaPanturrilhas() != null) progresso.setCircunferenciaPanturrilhas(request.getCircunferenciaPanturrilhas());
        if (request.getPercentualGordura() != null) progresso.setPercentualGordura(request.getPercentualGordura());
        if (request.getMassaMuscular() != null) progresso.setMassaMuscular(request.getMassaMuscular());
        if (request.getObservacoes() != null) progresso.setObservacoes(request.getObservacoes());

        progressoRepository.save(progresso);
        log.info("Progresso ID {} atualizado com sucesso!", id);
    }

    // ===== VALIDAÇÕES =====
    private void validarDadosProgresso(ProgressoRequest request) {
        if (request.getPeso() != null && request.getPeso() <= 0)
            throw new RuntimeException("Peso deve ser maior que zero.");
        if (request.getAltura() != null && request.getAltura() <= 0)
            throw new RuntimeException("Altura deve ser maior que zero.");
        if (request.getPercentualGordura() != null &&
                (request.getPercentualGordura() < 0 || request.getPercentualGordura() > 100))
            throw new RuntimeException("Percentual de gordura deve estar entre 0 e 100.");
    }

    // ===== CÁLCULOS =====
    private Double calcularIMC(Double peso, Double altura) {
        if (peso == null || altura == null || altura == 0) return null;
        double imc = peso / (altura * altura);
        return Math.round(imc * 100.0) / 100.0;
    }

    private String classificarIMC(Double imc) {
        if (imc == null) return null;
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25) return "Peso normal";
        if (imc < 30) return "Sobrepeso";
        if (imc < 35) return "Obesidade Grau I";
        if (imc < 40) return "Obesidade Grau II";
        return "Obesidade Grau III";
    }

    // ===== CONVERSÃO PARA RESPONSE =====
    private ProgressoResponse convertToResponse(Progresso progresso) {
        return ProgressoResponse.builder()
                .id(progresso.getId())
                .alunoId(progresso.getAluno().getId())
                .alunoNome(progresso.getAluno().getNome())
                .peso(progresso.getPeso())
                .altura(progresso.getAltura())
                .imc(progresso.getImc())
                .circunferenciaBracos(progresso.getCircunferenciaBracos())
                .circunferenciaPeito(progresso.getCircunferenciaPeito())
                .circunferenciaCintura(progresso.getCircunferenciaCintura())
                .circunferenciaQuadril(progresso.getCircunferenciaQuadril())
                .circunferenciaCoxas(progresso.getCircunferenciaCoxas())
                .circunferenciaPanturrilhas(progresso.getCircunferenciaPanturrilhas())
                .percentualGordura(progresso.getPercentualGordura())
                .massaMuscular(progresso.getMassaMuscular())
                .observacoes(progresso.getObservacoes())
                .dataRegistro(progresso.getDataRegistro())
                .classificacaoIMC(classificarIMC(progresso.getImc()))
                .build();
    }
}
