package com.maxfit.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_treinos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @Column(nullable = false)
    private LocalDate dataTreino;

    @Column(nullable = false)
    private String nomeTreino;

    @Column(nullable = false)
    private boolean concluido;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
    }
}
