package com.maxfit.services;

import com.maxfit.dto.response.DicaResponse;
import com.maxfit.dto.response.ProfissionalSuporteResponse;
import com.maxfit.dto.response.TutorialResponse;
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

    // ============================================================
    // PROFISSIONAIS MOCKADOS (PSICÓLOGOS + NUTRICIONISTAS)
    // ============================================================
    private final List<ProfissionalSuporteResponse> profissionais = Arrays.asList(

            // ==== PSICÓLOGOS ====
            ProfissionalSuporteResponse.builder()
                    .id(1L).nome("Gisele Santinoni")
                    .especialidade("Psicologia Clínica")
                    .telefone("(48) 99999-0001")
                    .email("gisele@exemplo.com")
                    .cidade("Florianópolis")
                    .tipo("PSICOLOGO")
                    .build(),

            ProfissionalSuporteResponse.builder()
                    .id(2L).nome("Mariana Souza")
                    .especialidade("Psicologia Esportiva")
                    .telefone("(11) 98888-0002")
                    .email("mariana@exemplo.com")
                    .cidade("São Paulo")
                    .tipo("PSICOLOGO")
                    .build(),

            // (todos os demais psicólogos que você já tinha continuam aqui...)
            // --------------------------------------------------------------

            ProfissionalSuporteResponse.builder()
                    .id(15L)
                    .nome("Thiago Ferreira")
                    .especialidade("Psicologia Comportamental")
                    .telefone("(48) 99111-2011")
                    .email("thiago.ferreira@exemplo.com")
                    .cidade("Treze de Maio")
                    .tipo("PSICOLOGO")
                    .build(),

            // ==== NUTRICIONISTAS ====
            ProfissionalSuporteResponse.builder()
                    .id(3L).nome("Paulo Lima")
                    .especialidade("Nutrição Esportiva")
                    .telefone("(48) 97777-0003")
                    .email("paulo@exemplo.com")
                    .cidade("Florianópolis")
                    .tipo("NUTRICIONISTA")
                    .build(),

            ProfissionalSuporteResponse.builder()
                    .id(4L).nome("Ana Clara")
                    .especialidade("Nutrição Clínica")
                    .telefone("(11) 96666-0004")
                    .email("ana@exemplo.com")
                    .cidade("São Paulo")
                    .tipo("NUTRICIONISTA")
                    .build(),

            // (todos os demais nutricionistas continuam aqui...)
            // --------------------------------------------------------------

            ProfissionalSuporteResponse.builder()
                    .id(26L)
                    .nome("Letícia Freitas")
                    .especialidade("Nutrição Clínica")
                    .telefone("(48) 99211-3011")
                    .email("leticia.freitas@exemplo.com")
                    .cidade("Treze de Maio")
                    .tipo("NUTRICIONISTA")
                    .build()
    );

    // ============================================================
    // LISTA DE TUTORIAIS MOCKADOS
    // ============================================================
    private final List<TutorialResponse> tutoriais = Arrays.asList(
            TutorialResponse.builder()
                    .id(1L)
                    .titulo("Como fazer supino corretamente")
                    .descricao("Técnica correta e erros comuns")
                    .url("https://www.youtube.com/watch?v=rT7DgCr-3pg")
                    .thumbnail("🎥")
                    .build(),

            TutorialResponse.builder()
                    .id(2L)
                    .titulo("Agachamento livre: guia completo")
                    .descricao("Passo a passo para iniciantes")
                    .url("https://www.youtube.com/watch?v=ultWZbUMPL8")
                    .thumbnail("🎥")
                    .build(),

            TutorialResponse.builder()
                    .id(3L)
                    .titulo("Alongamentos pré-treino")
                    .descricao("Prepare seu corpo corretamente")
                    .url("https://www.youtube.com/watch?v=xzTHQbvBpM8")
                    .thumbnail("🎥")
                    .build()
    );

    // ============================================================
    // LISTA DE DICAS MOCKADAS
    // ============================================================
    private final List<DicaResponse> dicas = Arrays.asList(
            DicaResponse.builder()
                    .id(1L)
                    .titulo("A importância da hidratação")
                    .descricao("Água faz diferença no desempenho e recuperação muscular.")
                    .categoria("Saúde")
                    .conteudo("Beba pelo menos 2L ao dia, e hidrate-se antes e após o treino.")
                    .build(),

            DicaResponse.builder()
                    .id(2L)
                    .titulo("Evite lesões na musculação")
                    .descricao("Como treinar com segurança e técnica correta.")
                    .categoria("Segurança")
                    .conteudo("Aqueça, mantenha técnica correta, aumente a carga de forma gradual.")
                    .build(),

            DicaResponse.builder()
                    .id(3L)
                    .titulo("Nutrição pré-treino")
                    .descricao("O que comer para melhor performance.")
                    .categoria("Nutrição")
                    .conteudo("Consuma carboidratos complexos e proteína leve antes do treino.")
                    .build()
    );

    // ============================================================
    // FUNÇÃO GENÉRICA PARA FILTRAR POR CIDADE E TIPO
    // ============================================================
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

    // ============================================================
    // MÉTODOS CHAMADOS PELO CONTROLLER
    // ============================================================
    public List<ProfissionalSuporteResponse> listarPsicologosPorCidade(String cidade) {
        log.info("Buscando psicólogos para cidade: {}", cidade);
        return filtrarPorCidadeETipo(cidade, "PSICOLOGO");
    }

    public List<ProfissionalSuporteResponse> listarNutricionistasPorCidade(String cidade) {
        log.info("Buscando nutricionistas para cidade: {}", cidade);
        return filtrarPorCidadeETipo(cidade, "NUTRICIONISTA");
    }

    public List<TutorialResponse> listarTutoriais() {
        log.info("Listando tutoriais");
        return tutoriais;
    }

    public List<DicaResponse> listarDicas() {
        log.info("Listando dicas");
        return dicas;
    }

    public DicaResponse buscarDicaPorId(Long id) {
        log.info("Buscando dica ID {}", id);
        return dicas.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
