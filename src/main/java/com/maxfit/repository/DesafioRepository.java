package com.maxfit.repository;

import com.maxfit.model.Desafio;
import com.maxfit.model.StatusDesafio;
import com.maxfit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesafioRepository extends JpaRepository<Desafio, Long> {

    // Busca todos os desafios de um aluno
    List<Desafio> findByAluno(Usuario aluno);

    // Busca desafios de um aluno por status
    List<Desafio> findByAlunoAndStatus(Usuario aluno, StatusDesafio status);

    // Busca desafios ativos de um aluno
    List<Desafio> findByAlunoAndStatusOrderByDataInicioDesc(Usuario aluno, StatusDesafio status);
}