package com.maxfit.repository;

import com.maxfit.model.CurtidaComunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurtidaComunidadeRepository extends JpaRepository<CurtidaComunidade, Long> {

    // Verifica se o usuário já curtiu a postagem
    boolean existsByPostagemIdAndAlunoId(Long postagemId, Long alunoId);

    // Busca uma curtida específica
    Optional<CurtidaComunidade> findByPostagemIdAndAlunoId(Long postagemId, Long alunoId);

    // Conta quantas curtidas uma postagem tem
    long countByPostagemId(Long postagemId);

    // Deleta curtida de um usuário em uma postagem
    void deleteByPostagemIdAndAlunoId(Long postagemId, Long alunoId);
}