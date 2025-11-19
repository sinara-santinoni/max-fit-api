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