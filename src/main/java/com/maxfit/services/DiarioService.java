package com.maxfit.services;

import com.maxfit.dto.request.DiarioRequest;
import com.maxfit.dto.response.DiarioResponse;
import com.maxfit.model.Diario;
import com.maxfit.repository.DiarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiarioService {

    private final DiarioRepository diarioRepository;

    public List<DiarioResponse> buscarDiarios(Long alunoId) {
        log.info("Buscando diários do aluno: {}", alunoId);

        List<Diario> diarios = diarioRepository.findByAlunoIdOrderByDataDesc(alunoId);

        return diarios.stream()
                .map(this::toDiarioResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public DiarioResponse registrarDiario(DiarioRequest request) {
        log.info("Registrando entrada no diário para aluno: {}", request.getAlunoId());

        Diario diario = Diario.builder()
                .alunoId(request.getAlunoId())
                .data(request.getData())
                .treinoExecutado(request.getTreinoExecutado())
                .avaliacao(request.getAvaliacao())
                .objetivo(request.getObjetivo())
                .feitoHoje(request.getFeitoHoje())
                .comoMeSenti(request.getComoMeSenti())
                .imagem(request.getImagem())
                .build();

        Diario salvo = diarioRepository.save(diario);

        log.info("Entrada registrada com sucesso!");

        return toDiarioResponse(salvo);
    }

    private DiarioResponse toDiarioResponse(Diario diario) {
        return DiarioResponse.builder()
                .id(diario.getId())
                .alunoId(diario.getAlunoId())
                .data(diario.getData())
                .treinoExecutado(diario.getTreinoExecutado())
                .avaliacao(diario.getAvaliacao())
                .objetivo(diario.getObjetivo())
                .feitoHoje(diario.getFeitoHoje())
                .comoMeSenti(diario.getComoMeSenti())
                .imagem(diario.getImagem())
                .build();
    }
}
