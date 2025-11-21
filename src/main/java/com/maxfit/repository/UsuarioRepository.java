package com.maxfit.repository;

import com.maxfit.model.TipoUsuario;
import com.maxfit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    // 🔹 Alunos SEM personal
    List<Usuario> findByTipoAndPersonalIdIsNull(TipoUsuario tipo);

    // 🔹 Alunos COM personal
    List<Usuario> findByTipoAndPersonalId(TipoUsuario tipo, Long personalId);

    boolean existsByEmail(String email);

    // 🔹 NOVO — Buscar TODOS os usuários do tipo ALUNO
    List<Usuario> findByTipo(TipoUsuario tipo);
}
