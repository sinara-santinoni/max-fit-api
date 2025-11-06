package com.maxfit.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "diarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aluno_id", nullable = false)
    private Long alunoId;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "treino_executado", nullable = false)
    private String treinoExecutado;

    private Integer avaliacao;

    @Column(columnDefinition = "TEXT")
    private String objetivo;

    @Column(name = "feito_hoje", columnDefinition = "TEXT")
    private String feitoHoje;

    @Column(name = "como_me_senti", columnDefinition = "TEXT")
    private String comoMeSenti;

    @Column(columnDefinition = "TEXT")
    private String imagem;
}
