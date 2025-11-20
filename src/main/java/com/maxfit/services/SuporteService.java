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

    // Lista "mockada" de profissionais
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
            ProfissionalSuporteResponse.builder()
                    .id(5L)
                    .nome("Carlos Eduardo Silva")
                    .especialidade("Psicologia Clínica")
                    .telefone("(48) 99101-2001")
                    .email("carlos.silva@exemplo.com")
                    .cidade("Tubarão")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(6L)
                    .nome("Juliana Martins")
                    .especialidade("Psicologia Comportamental")
                    .telefone("(48) 99102-2002")
                    .email("juliana.martins@exemplo.com")
                    .cidade("Gravatal")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(7L)
                    .nome("Roberto Alves")
                    .especialidade("Psicologia Esportiva")
                    .telefone("(48) 99103-2003")
                    .email("roberto.alves@exemplo.com")
                    .cidade("Imbituba")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(8L)
                    .nome("Fernanda Costa")
                    .especialidade("Psicologia Clínica")
                    .telefone("(48) 99104-2004")
                    .email("fernanda.costa@exemplo.com")
                    .cidade("Laguna")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(9L)
                    .nome("André Souza")
                    .especialidade("Psicologia Organizacional")
                    .telefone("(48) 99105-2005")
                    .email("andre.souza@exemplo.com")
                    .cidade("Jaguaruna")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(10L)
                    .nome("Patrícia Lima")
                    .especialidade("Psicologia Infantil")
                    .telefone("(48) 99106-2006")
                    .email("patricia.lima@exemplo.com")
                    .cidade("Imaruí")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(11L)
                    .nome("Lucas Pereira")
                    .especialidade("Psicologia Clínica")
                    .telefone("(48) 99107-2007")
                    .email("lucas.pereira@exemplo.com")
                    .cidade("Orleans")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(12L)
                    .nome("Camila Rodrigues")
                    .especialidade("Psicologia Esportiva")
                    .telefone("(48) 99108-2008")
                    .email("camila.rodrigues@exemplo.com")
                    .cidade("São Ludgero")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(13L)
                    .nome("Rafael Santos")
                    .especialidade("Psicologia do Desenvolvimento")
                    .telefone("(48) 99109-2009")
                    .email("rafael.santos@exemplo.com")
                    .cidade("Pedras Grandes")
                    .tipo("PSICOLOGO")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(14L)
                    .nome("Beatriz Oliveira")
                    .especialidade("Psicologia Clínica")
                    .telefone("(48) 99110-2010")
                    .email("beatriz.oliveira@exemplo.com")
                    .cidade("Capivari de Baixo")
                    .tipo("PSICOLOGO")
                    .build(),
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
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(16L)
                    .nome("Marina Gonçalves")
                    .especialidade("Nutrição Esportiva")
                    .telefone("(48) 99201-3001")
                    .email("marina.goncalves@exemplo.com")
                    .cidade("Tubarão")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(17L)
                    .nome("Felipe Barbosa")
                    .especialidade("Nutrição Clínica")
                    .telefone("(48) 99202-3002")
                    .email("felipe.barbosa@exemplo.com")
                    .cidade("Gravatal")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(18L)
                    .nome("Larissa Mendes")
                    .especialidade("Nutrição Funcional")
                    .telefone("(48) 99203-3003")
                    .email("larissa.mendes@exemplo.com")
                    .cidade("Imbituba")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(19L)
                    .nome("Gabriel Cardoso")
                    .especialidade("Nutrição Esportiva")
                    .telefone("(48) 99204-3004")
                    .email("gabriel.cardoso@exemplo.com")
                    .cidade("Laguna")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(20L)
                    .nome("Isabela Campos")
                    .especialidade("Nutrição Clínica")
                    .telefone("(48) 99205-3005")
                    .email("isabela.campos@exemplo.com")
                    .cidade("Jaguaruna")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(21L)
                    .nome("Henrique Araújo")
                    .especialidade("Nutrição Vegetariana")
                    .telefone("(48) 99206-3006")
                    .email("henrique.araujo@exemplo.com")
                    .cidade("Imaruí")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(22L)
                    .nome("Júlia Moreira")
                    .especialidade("Nutrição Esportiva")
                    .telefone("(48) 99207-3007")
                    .email("julia.moreira@exemplo.com")
                    .cidade("Orleans")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(23L)
                    .nome("Diego Nascimento")
                    .especialidade("Nutrição Clínica")
                    .telefone("(48) 99208-3008")
                    .email("diego.nascimento@exemplo.com")
                    .cidade("São Ludgero")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(24L)
                    .nome("Amanda Reis")
                    .especialidade("Nutrição Funcional")
                    .telefone("(48) 99209-3009")
                    .email("amanda.reis@exemplo.com")
                    .cidade("Pedras Grandes")
                    .tipo("NUTRICIONISTA")
                    .build(),
            ProfissionalSuporteResponse.builder()
                    .id(25L)
                    .nome("Vinicius Carvalho")
                    .especialidade("Nutrição Esportiva")
                    .telefone("(48) 99210-3010")
                    .email("vinicius.carvalho@exemplo.com")
                    .cidade("Capivari de Baixo")
                    .tipo("NUTRICIONISTA")
                    .build(),
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

    // Lista de tutoriais
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
                    .build(),
            TutorialResponse.builder()
                    .id(4L)
                    .titulo("Levantamento terra: execução perfeita")
                    .descricao("Como fazer deadlift com segurança")
                    .url("https://www.youtube.com/watch?v=op9kVnSso6Q")
                    .thumbnail("🎥")
                    .build(),
            TutorialResponse.builder()
                    .id(5L)
                    .titulo("Rosca direta para bíceps")
                    .descricao("Maximize o crescimento dos braços")
                    .url("https://www.youtube.com/watch?v=ykJmrZ5v0Oo")
                    .thumbnail("🎥")
                    .build(),
            TutorialResponse.builder()
                    .id(6L)
                    .titulo("Desenvolvimento militar correto")
                    .descricao("Fortaleça seus ombros com segurança")
                    .url("https://www.youtube.com/watch?v=qEwKCR5JCog")
                    .thumbnail("🎥")
                    .build()
    );

    // Lista de dicas
    private final List<DicaResponse> dicas = Arrays.asList(
            DicaResponse.builder()
                    .id(1L)
                    .titulo("A importância da hidratação")
                    .descricao("Beber água antes, durante e após o treino é essencial para manter o desempenho e a recuperação muscular. Mantenha-se hidratado durante todo o dia.")
                    .categoria("Saúde")
                    .conteudo("A hidratação adequada é crucial para o desempenho físico. Durante o exercício, perdemos água através do suor, e essa perda precisa ser reposta. Beba pelo menos 500ml de água 2 horas antes do treino, pequenos goles durante o exercício, e reponha 150% do peso perdido após o treino.")
                    .build(),
            DicaResponse.builder()
                    .id(2L)
                    .titulo("Como evitar lesões na musculação")
                    .descricao("Dicas de prevenção e cuidados importantes para treinar com segurança e evitar afastamentos.")
                    .categoria("Segurança")
                    .conteudo("Prevenção de lesões: sempre faça aquecimento adequado (5-10 min), utilize a técnica correta antes de aumentar a carga, respeite os dias de descanso, alongue após o treino, e escute seu corpo - dor não é normal.")
                    .build(),
            DicaResponse.builder()
                    .id(3L)
                    .titulo("Nutrição pré-treino")
                    .descricao("O que comer antes de treinar para melhor performance e energia durante os exercícios.")
                    .categoria("Nutrição")
                    .conteudo("Consuma carboidratos complexos 1-2 horas antes (aveia, batata-doce) para energia sustentada, adicione proteína magra (frango, ovos) e evite gorduras em excesso. Uma banana 30min antes também é excelente para energia rápida.")
                    .build(),
            DicaResponse.builder()
                    .id(4L)
                    .titulo("Descanso e recuperação muscular")
                    .descricao("Entenda porque dormir bem é tão importante quanto treinar para ganhar músculos.")
                    .categoria("Saúde")
                    .conteudo("O músculo cresce durante o descanso, não no treino. Durma 7-9 horas por noite, respeite 48h entre treinos do mesmo grupo muscular, e considere técnicas de recuperação como alongamento, massagem e banhos de contraste.")
                    .build(),
            DicaResponse.builder()
                    .id(5L)
                    .titulo("Suplementação básica para iniciantes")
                    .descricao("Quais suplementos realmente fazem diferença e como utilizá-los corretamente.")
                    .categoria("Nutrição")
                    .conteudo("Para iniciantes, foque no básico: Whey Protein (pós-treino ou para bater meta proteica), Creatina 5g/dia (melhora força e recuperação), e um multivitamínico. Lembre-se: suplementos complementam, não substituem uma boa alimentação.")
                    .build(),
            DicaResponse.builder()
                    .id(6L)
                    .titulo("Progressão de carga adequada")
                    .descricao("Como aumentar os pesos de forma segura e eficiente para evoluir nos treinos.")
                    .categoria("Segurança")
                    .conteudo("Aumente a carga gradualmente: 2-5% por semana ou quando conseguir fazer 2-3 repetições a mais que o planejado com boa técnica. Priorize sempre a execução correta sobre o peso. Anote seus treinos para acompanhar a evolução.")
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

    public List<TutorialResponse> listarTutoriais() {
        log.info("Listando todos os tutoriais");
        return tutoriais;
    }

    public List<DicaResponse> listarDicas() {
        log.info("Listando todas as dicas");
        return dicas;
    }

    public DicaResponse buscarDicaPorId(Long id) {
        log.info("Buscando dica com ID: {}", id);
        return dicas.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}