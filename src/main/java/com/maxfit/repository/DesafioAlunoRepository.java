package com.maxfit.repository;

import com.maxfit.model.DesafioAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DesafioAlunoRepository extends JpaRepository<DesafioAluno, Long> {

    Optional<DesafioAluno> findByDesafioIdAndAlunoId(Long desafioId, Long alunoId);

    boolean existsByDesafioIdAndAlunoId(Long desafioId, Long alunoId);
}
