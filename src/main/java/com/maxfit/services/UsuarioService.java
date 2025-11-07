package com.maxfit.services;

import com.maxfit.dto.request.CadastroRequest;
import com.maxfit.dto.request.LoginRequest;
import com.maxfit.dto.request.VincularAlunoRequest;
import com.maxfit.dto.response.AlunoResponse;
import com.maxfit.dto.response.CadastroResponse;
import com.maxfit.dto.response.LoginResponse;
import com.maxfit.model.TipoUsuario;
import com.maxfit.model.Usuario;
import com.maxfit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // ===== CADASTRAR USUÁRIO =====
    @Transactional
    public CadastroResponse cadastrar(CadastroRequest request) {
        log.info("Iniciando cadastro de usuário: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return CadastroResponse.builder()
                    .sucesso(false)
                    .mensagem("E-mail já cadastrado.")
                    .build();
        }

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipo(request.getTipo())
                .build();

        Usuario salvo = usuarioRepository.save(usuario);

        log.info("Usuário cadastrado com sucesso: ID {}", salvo.getId());

        return CadastroResponse.builder()
                .sucesso(true)
                .mensagem("Usuário cadastrado com sucesso!")
                .id(salvo.getId())
                .build();
    }

    // ===== LOGIN =====
    public LoginResponse login(LoginRequest request) {
        log.info("Tentativa de login: {}", request.getEmail());

        Usuario usuario = usuarioRepository.findByEmailAndSenha(request.getEmail(), request.getSenha())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos."));

        log.info("Login realizado: {}", usuario.getNome());

        return LoginResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .tipo(usuario.getTipo())
                .build();
    }

    // ===== LISTAR TODOS OS USUÁRIOS =====
    public List<Usuario> listarTodos() {
        log.info("Listando todos os usuários...");
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.info("Total de usuários encontrados: {}", usuarios.size());
        return usuarios;
    }

    // ===== BUSCAR ALUNOS DISPONÍVEIS =====
    public List<AlunoResponse> buscarAlunosDisponiveis() {
        log.info("Buscando alunos disponíveis");

        List<Usuario> alunos = usuarioRepository.findByTipoAndPersonalIdIsNull(TipoUsuario.ALUNO);

        log.info("Alunos disponíveis: {}", alunos.size());

        return alunos.stream()
                .map(this::toAlunoResponse)
                .collect(Collectors.toList());
    }

    // ===== BUSCAR ALUNOS DE UM PERSONAL =====
    public List<AlunoResponse> buscarAlunosDoPersonal(Long idPersonal) {
        log.info("Buscando alunos do personal: {}", idPersonal);

        List<Usuario> alunos = usuarioRepository.findByTipoAndPersonalId(TipoUsuario.ALUNO, idPersonal);

        log.info("Alunos do personal {}: {}", idPersonal, alunos.size());

        return alunos.stream()
                .map(this::toAlunoResponse)
                .collect(Collectors.toList());
    }

    // ===== VINCULAR ALUNO =====
    @Transactional
    public void vincularAluno(VincularAlunoRequest request) {
        log.info("Vinculando aluno {} ao personal {}", request.getAlunoId(), request.getPersonalId());

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (aluno.getTipo() != TipoUsuario.ALUNO) {
            throw new RuntimeException("Usuário não é um aluno");
        }

        aluno.setPersonalId(request.getPersonalId());
        usuarioRepository.save(aluno);

        log.info("Aluno {} vinculado ao personal {}", request.getAlunoId(), request.getPersonalId());
    }

    // ===== REMOVER ALUNO =====
    @Transactional
    public void removerAluno(Long idAluno) {
        log.info("Removendo vínculo do aluno: {}", idAluno);

        Usuario aluno = usuarioRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.setPersonalId(null);
        usuarioRepository.save(aluno);

        log.info("Aluno {} desvinculado do personal", idAluno);
    }

    // ===== CONVERSÃO DE ENTIDADE PARA RESPONSE =====
    private AlunoResponse toAlunoResponse(Usuario usuario) {
        return AlunoResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }
}
