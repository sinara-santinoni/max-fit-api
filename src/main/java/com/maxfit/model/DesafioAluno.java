package com.maxfit.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "desafios_aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesafioAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "desafio_id", nullable = false)
    private Long desafioId;

    @Column(name = "aluno_id", nullable = false)
    private Long alunoId;

    @Column(name = "data_participacao")
    private LocalDate dataParticipacao;

    @PrePersist
    protected void onCreate() {
        if (dataParticipacao == null) {
            dataParticipacao = LocalDate.now();
        }
    }
}