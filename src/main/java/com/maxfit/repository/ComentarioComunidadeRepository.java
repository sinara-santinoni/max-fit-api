package com.maxfit.repository;

import com.maxfit.model.ComentarioComunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioComunidadeRepository extends JpaRepository<ComentarioComunidade, Long> {

    List<ComentarioComunidade> findByPostagemIdOrderByDataCriacaoAsc(Long postagemId);

    int countByPostagemId(Long postagemId);}
