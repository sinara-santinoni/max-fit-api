package com.maxfit.repository;

import com.maxfit.model.Diario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiarioRepository extends JpaRepository<Diario, Long> {

    List<Diario> findByAlunoIdOrderByDataDesc(Long alunoId);
}
