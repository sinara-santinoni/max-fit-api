package com.maxfit.repository;

import com.maxfit.model.PostagemComunidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostagemComunidadeRepository extends JpaRepository<PostagemComunidade, Long> {

    List<PostagemComunidade> findAllByOrderByDataCriacaoDesc();
}
