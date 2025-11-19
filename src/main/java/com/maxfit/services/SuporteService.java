package com.maxfit.services;

import com.maxfit.dto.response.ProfissionalSuporteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuporteService {

    // Lista "mockada" de profissionais (poderia vir do banco futuramente)
    private final List<ProfissionalSuporteResponse> profissionais = Arrays.asList(
            // ==== PSICÓLOGOS ====
            ProfissionalSuporteResponse.builder()
                    .id(1L)
                    .nome("Gisele Santinoni")
                    .especialidade("Psicologia Clínica")
                    .telefone("(48) 99999-0001")
                    .email("gisele@exemplo.com")
                    .cidade("Florianópolis")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(2L)
                    .nome("Mariana Souza")
                    .especialidade("Psicologia Esportiva")
                    .telefone("(11) 98888-0002")
                    .email("mariana@exemplo.com")
                    .cidade("São Paulo")
                    .tipo("PSICOLOGO")
                    .build(),
            // ==== NUTRICIONISTAS ====
            ProfissionalSuporteResponse.builder()
                    .id(3L)
                    .nome("Paulo Lima")
                    .especialidade("Nutrição Esportiva")
                    .telefone("(48) 97777-0003")
                    .email("paulo@exemplo.com")
                    .cidade("Florianópolis")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(4L)
                    .nome("Ana Clara")
                    .especialidade("Nutrição Clínica")
                    .telefone("(11) 96666-0004")
                    .email("ana@exemplo.com")
                    .cidade("São Paulo")
                    .tipo("NUTRICIONISTA")
                    .build()
    );

    private List<ProfissionalSuporteResponse> filtrarPorCidadeETipo(String cidade, String tipo) {
        if (cidade == null || cidade.isBlank()) {
            cidade = "";
        }
        String cidadeLower = cidade.toLowerCase(Locale.ROOT);

        return profissionais.stream()
                .filter(p -> p.getTipo().equalsIgnoreCase(tipo))
                .filter(p -> p.getCidade() != null &&
                        p.getCidade().toLowerCase(Locale.ROOT).contains(cidadeLower))
                .collect(Collectors.toList());
    }

    public List<ProfissionalSuporteResponse> listarPsicologosPorCidade(String cidade) {
        log.info("Buscando psicólogos para a cidade: {}", cidade);
        return filtrarPorCidadeETipo(cidade, "PSICOLOGO");
    }

    public List<ProfissionalSuporteResponse> listarNutricionistasPorCidade(String cidade) {
        log.info("Buscando nutricionistas para a cidade: {}", cidade);
        return filtrarPorCidadeETipo(cidade, "NUTRICIONISTA");
    }
}
