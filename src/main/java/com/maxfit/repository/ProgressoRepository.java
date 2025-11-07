package com.maxfit.repository;

import com.maxfit.model.Progresso;
import com.maxfit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressoRepository extends JpaRepository<Progresso, Long> {

    // Busca todos os registros de progresso de um aluno, ordenado por data (mais recente primeiro)
    List<Progresso> findByAlunoOrderByDataRegistroDesc(Usuario aluno);

    // Busca o progresso mais recente de um aluno
    Progresso findFirstByAlunoOrderByDataRegistroDesc(Usuario aluno);
}