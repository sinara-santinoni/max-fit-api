package com.maxfit.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardTreinoResponse {

    private int treinosSemana;
    private int metaSemana;

    private int treinosMes;
    private int metaMes;

    private int streakDias;

    private List<UltimosTreinosResponse> ultimosTreinos;
}