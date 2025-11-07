package com.maxfit.repository;


import com.maxfit.Progresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgressoRepository extends JpaRepository<Progresso, Long> {

    List<Progresso> findByAlunoIdOrderByDataRegistroDesc(Long alunoId);
}
