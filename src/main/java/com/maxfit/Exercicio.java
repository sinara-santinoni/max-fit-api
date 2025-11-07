package com.maxfit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treino_id", nullable = false)
    @ToString.Exclude
    private Treino treino;

    @Column(nullable = false)
    private String nome;

    private Integer series;

    private Integer repeticoes;

    private Integer descanso;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}

