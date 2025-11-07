package com.maxfit.services;

import com.maxfit.dto.request.ProgressoRequest;
import com.maxfit.dto.request.ProgressoUpdateRequest;
import com.maxfit.dto.response.ProgressoResponse;
import com.maxfit.model.Progresso;
import com.maxfit.model.Usuario;
import com.maxfit.repository.ProgressoRepository;
import com.maxfit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ProgressoResponse> buscarProgresso(Long alunoId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return progressoRepository.findByAlunoOrderByDataRegistroDesc(aluno).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProgressoResponse cadastrarProgresso(ProgressoRequest request) {
        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Validações
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

        Progresso progressoSalvo = progressoRepository.save(progresso);
        return convertToResponse(progressoSalvo);
    }

    @Transactional
    public void atualizarProgresso(Long id, ProgressoUpdateRequest request) {
        Progresso progresso = progressoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de progresso não encontrado"));

        // Atualizar apenas os campos fornecidos
        if (request.getPeso() != null) {
            progresso.setPeso(request.getPeso());
        }
        if (request.getAltura() != null) {
            progresso.setAltura(request.getAltura());
        }

        // Recalcular IMC se peso ou altura foram alterados
        if (request.getPeso() != null || request.getAltura() != null) {
            progresso.setImc(calcularIMC(progresso.getPeso(), progresso.getAltura()));
        }

        if (request.getCircunferenciaBracos() != null) {
            progresso.setCircunferenciaBracos(request.getCircunferenciaBracos());
        }
        if (request.getCircunferenciaPeito() != null) {
            progresso.setCircunferenciaPeito(request.getCircunferenciaPeito());
        }
        if (request.getCircunferenciaCintura() != null) {
            progresso.setCircunferenciaCintura(request.getCircunferenciaCintura());
        }
        if (request.getCircunferenciaQuadril() != null) {
            progresso.setCircunferenciaQuadril(request.getCircunferenciaQuadril());
        }
        if (request.getCircunferenciaCoxas() != null) {
            progresso.setCircunferenciaCoxas(request.getCircunferenciaCoxas());
        }
        if (request.getCircunferenciaPanturrilhas() != null) {
            progresso.setCircunferenciaPanturrilhas(request.getCircunferenciaPanturrilhas());
        }
        if (request.getPercentualGordura() != null) {
            progresso.setPercentualGordura(request.getPercentualGordura());
        }
        if (request.getMassaMuscular() != null) {
            progresso.setMassaMuscular(request.getMassaMuscular());
        }
        if (request.getObservacoes() != null) {
            progresso.setObservacoes(request.getObservacoes());
        }

        progressoRepository.save(progresso);
    }

    private void validarDadosProgresso(ProgressoRequest request) {
        if (request.getPeso() != null && request.getPeso() <= 0) {
            throw new RuntimeException("Peso deve ser maior que zero");
        }
        if (request.getAltura() != null && request.getAltura() <= 0) {
            throw new RuntimeException("Altura deve ser maior que zero");
        }
        if (request.getPercentualGordura() != null &&
                (request.getPercentualGordura() < 0 || request.getPercentualGordura() > 100)) {
            throw new RuntimeException("Percentual de gordura deve estar entre 0 e 100");
        }
    }

    private Double calcularIMC(Double peso, Double altura) {
        if (peso == null || altura == null || altura == 0) {
            return null;
        }
        // IMC = peso / (altura²)
        double imc = peso / (altura * altura);
        return Math.round(imc * 100.0) / 100.0; // Arredonda para 2 casas decimais
    }

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

    private String classificarIMC(Double imc) {
        if (imc == null) {
            return null;
        }
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else if (imc < 35) {
            return "Obesidade Grau I";
        } else if (imc < 40) {
            return "Obesidade Grau II";
        } else {
            return "Obesidade Grau III";
        }
    }
}