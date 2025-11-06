package com.maxfit.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "progresso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aluno_id", nullable = false)
    private Long alunoId;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(nullable = false)
    private String exercicio;

    private Double peso;

    private Integer repeticoes;

    private Integer series;

    private Integer rpe;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}
