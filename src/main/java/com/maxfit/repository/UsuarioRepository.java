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

    List<Usuario> findByTipoAndPersonalIdIsNull(TipoUsuario tipo);

    List<Usuario> findByTipoAndPersonalId(TipoUsuario tipo, Long personalId);

    boolean existsByEmail(String email);}
