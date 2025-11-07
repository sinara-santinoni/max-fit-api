package com.maxfit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "progresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private Double altura;

    private Double imc;

    private Double circunferenciaBracos;
    private Double circunferenciaPeito;
    private Double circunferenciaCintura;
    private Double circunferenciaQuadril;
    private Double circunferenciaCoxas;
    private Double circunferenciaPanturrilhas;

    private Double percentualGordura;
    private Double massaMuscular;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;
}