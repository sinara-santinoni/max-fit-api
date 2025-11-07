package com.maxfit.repository;

import com.maxfit.model.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    List<Exercicio> findByTreinoId(Long treinoId);
}
