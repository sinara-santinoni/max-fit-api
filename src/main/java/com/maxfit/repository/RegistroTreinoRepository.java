package com.maxfit.repository;

import com.maxfit.model.RegistroTreino;
import com.maxfit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroTreinoRepository extends JpaRepository<RegistroTreino, Long> {

    List<RegistroTreino> findByAlunoOrderByDataTreinoDesc(Usuario aluno);

    List<RegistroTreino> findByAlunoAndDataTreinoBetweenOrderByDataTreinoDesc(
            Usuario aluno, LocalDate inicio, LocalDate fim);

    RegistroTreino findFirstByAlunoOrderByDataTreinoDesc(Usuario aluno);
}
