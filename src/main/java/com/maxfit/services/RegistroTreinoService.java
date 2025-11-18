package com.maxfit.services;

import com.maxfit.dto.request.TreinoRegistroRequest;
import com.maxfit.dto.response.DashboardTreinoResponse;
import com.maxfit.dto.response.UltimosTreinosResponse;
import com.maxfit.model.RegistroTreino;
import com.maxfit.model.Usuario;
import com.maxfit.repository.RegistroTreinoRepository;
import com.maxfit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistroTreinoService {

    private final RegistroTreinoRepository registroTreinoRepository;
    private final UsuarioRepository usuarioRepository;

    // ===== REGISTRAR TREINO =====
    @Transactional
    public void registrarTreino(TreinoRegistroRequest request) {
        log.info("Registrando treino para aluno ID: {}", request.getAlunoId());

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        RegistroTreino treino = RegistroTreino.builder()
                .aluno(aluno)
                .dataTreino(LocalDate.now())
                .nomeTreino(request.getNomeTreino())
                .concluido(request.isConcluido())
                .build();

        registroTreinoRepository.save(treino);
        log.info("Treino registrado com sucesso!");
    }

    // ===== DASHBOARD (semana + mês + streak + últimos treinos) =====
    public DashboardTreinoResponse dashboard(Long alunoId) {
        log.info("Gerando dashboard para aluno ID: {}", alunoId);

        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        LocalDate hoje = LocalDate.now();

        // Semana (segunda a domingo)
        LocalDate inicioSemana = hoje.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = hoje.with(DayOfWeek.SUNDAY);

        List<RegistroTreino> semana = registroTreinoRepository
                .findByAlunoAndDataTreinoBetweenOrderByDataTreinoDesc(aluno, inicioSemana, fimSemana);

        // Mês
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        List<RegistroTreino> mes = registroTreinoRepository
                .findByAlunoAndDataTreinoBetweenOrderByDataTreinoDesc(aluno, inicioMes, fimMes);

        // Streak (dias seguidos treinando)
        int streak = calcularStreak(aluno);

        // Últimos 5 treinos
        List<UltimosTreinosResponse> ultimos = registroTreinoRepository
                .findByAlunoOrderByDataTreinoDesc(aluno)
                .stream()
                .limit(5)
                .map(t -> UltimosTreinosResponse.builder()
                        .data(t.getDataTreino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .nome(t.getNomeTreino())
                        .concluido(t.isConcluido())
                        .build())
                .collect(Collectors.toList());

        return DashboardTreinoResponse.builder()
                .treinosSemana((int) semana.stream().filter(RegistroTreino::isConcluido).count())
                .metaSemana(4)
                .treinosMes((int) mes.stream().filter(RegistroTreino::isConcluido).count())
                .metaMes(12)
                .streakDias(streak)
                .ultimosTreinos(ultimos)
                .build();
    }

    // ===== CÁLCULO DE STREAK (dias consecutivos com treino) =====
    private int calcularStreak(Usuario aluno) {
        List<RegistroTreino> treinos = registroTreinoRepository
                .findByAlunoOrderByDataTreinoDesc(aluno);

        if (treinos.isEmpty()) {
            return 0;
        }

        int streak = 0;
        LocalDate diaEsperado = LocalDate.now();

        for (RegistroTreino t : treinos) {
            if (t.getDataTreino().equals(diaEsperado)) {
                streak++;
                diaEsperado = diaEsperado.minusDays(1);
            } else if (t.getDataTreino().isBefore(diaEsperado)) {
                break;
            }
        }

        return streak;
    }
}
