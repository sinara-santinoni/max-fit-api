package com.maxfit.repository;

import com.maxfit.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {

    List<Treino> findByAlunoIdOrderByIdDesc(Long alunoId);

    List<Treino> findByPersonalIdOrderByIdDesc(Long personalId);
}

