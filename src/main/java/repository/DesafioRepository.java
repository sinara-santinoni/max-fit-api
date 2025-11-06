package repository;

import com.maxfit.model.Desafio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DesafioRepository extends JpaRepository<Desafio, Long> {

    List<Desafio> findAllByOrderByDataFimAsc();

    @Query("SELECT d FROM Desafio d WHERE d.alunoId = :alunoId " +
            "OR d.id IN (SELECT da.desafioId FROM DesafioAluno da WHERE da.alunoId = :alunoId) " +
            "ORDER BY d.dataFim ASC")
    List<Desafio> findDesafiosByAlunoId(@Param("alunoId") Long alunoId);
}
