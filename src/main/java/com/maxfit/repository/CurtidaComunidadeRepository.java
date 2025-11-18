package com.maxfit.repository;

import com.maxfit.model.CurtidaComunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurtidaComunidadeRepository extends JpaRepository<CurtidaComunidade, Long> {

    // Verifica se o usuário já curtiu a postagem
    boolean existsByPostagem_IdAndUsuario_Id(Long postagemId, Long usuarioId);

    // Busca curtida específica do usuário
    Optional<CurtidaComunidade> findByPostagem_IdAndUsuario_Id(Long postagemId, Long usuarioId);

    // Conta quantas curtidas uma postagem tem
    long countByPostagem_Id(Long postagemId);

    // Remove curtida específica
    void deleteByPostagem_IdAndUsuario_Id(Long postagemId, Long usuarioId);
}
