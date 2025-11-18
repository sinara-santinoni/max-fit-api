package com.maxfit.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "curtidas_comunidade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"postagem_id", "usuario_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurtidaComunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postagem_id", nullable = false)
    private PostagemComunidade postagem;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}