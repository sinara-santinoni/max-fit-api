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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // 🔹 Tokens ativos na memória
    private static final ConcurrentHashMap<String, Long> sessoesAtivas = new ConcurrentHashMap<>();

    // 🔹 Sessão expira em 4 horas
    private static final long TEMPO_EXPIRACAO = 1000L * 60 * 60 * 4;


    // ============================================================
    // CADASTRAR USUÁRIO
    // ============================================================
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
                .cidade(request.getCidade())
                .build();

        Usuario salvo = usuarioRepository.save(usuario);

        return CadastroResponse.builder()
                .sucesso(true)
                .mensagem("Usuário cadastrado com sucesso!")
                .id(salvo.getId())
                .build();
    }


    // ============================================================
    // LOGIN
    // ============================================================
    public LoginResponse login(LoginRequest request) {
        log.info("Tentativa de login: {}", request.getEmail());

        Usuario usuario = usuarioRepository
                .findByEmailAndSenha(request.getEmail(), request.getSenha())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos."));

        // gera token
        String token = UUID.randomUUID().toString();
        sessoesAtivas.put(token, System.currentTimeMillis() + TEMPO_EXPIRACAO);

        return LoginResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .tipo(usuario.getTipo())
                .cidade(usuario.getCidade())
                .token(token)
                .build();
    }


    // ============================================================
    // SESSÃO
    // ============================================================
    public boolean sessaoValida(String token) {
        if (token == null || token.isEmpty()) return false;
        Long expiraEm = sessoesAtivas.get(token);
        if (expiraEm == null) return false;
        if (System.currentTimeMillis() > expiraEm) {
            sessoesAtivas.remove(token);
            return false;
        }
        return true;
    }

    public void logout(String token) {
        sessoesAtivas.remove(token);
    }


    // ============================================================
    // NOVO: BUSCAR TODOS OS ALUNOS
    // ============================================================
    public List<AlunoResponse> buscarTodosOsAlunos() {
        log.info("Buscando TODOS os alunos cadastrados");

        List<Usuario> alunos = usuarioRepository.findByTipo(TipoUsuario.ALUNO);

        return alunos.stream()
                .map(this::toAlunoResponse)
                .collect(Collectors.toList());
    }


    // ============================================================
    // BUSCAR ALUNOS DISPONÍVEIS (sem personal)
    // ============================================================
    public List<AlunoResponse> buscarAlunosDisponiveis() {
        log.info("Buscando alunos disponíveis");

        List<Usuario> alunos = usuarioRepository.findByTipoAndPersonalIdIsNull(TipoUsuario.ALUNO);

        return alunos.stream()
                .map(this::toAlunoResponse)
                .collect(Collectors.toList());
    }


    // ============================================================
    // BUSCAR ALUNOS DO PERSONAL
    // ============================================================
    public List<AlunoResponse> buscarAlunosDoPersonal(Long idPersonal) {
        log.info("Buscando alunos do personal {}", idPersonal);

        List<Usuario> alunos = usuarioRepository.findByTipoAndPersonalId(TipoUsuario.ALUNO, idPersonal);

        return alunos.stream()
                .map(this::toAlunoResponse)
                .collect(Collectors.toList());
    }


    // ============================================================
    // VINCULAR ALUNO AO PERSONAL
    // ============================================================
    @Transactional
    public void vincularAluno(VincularAlunoRequest request) {

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (aluno.getTipo() != TipoUsuario.ALUNO) {
            throw new RuntimeException("Usuário informado não é aluno.");
        }

        aluno.setPersonalId(request.getPersonalId());
        usuarioRepository.save(aluno);
    }


    // ============================================================
    // DESVINCULAR ALUNO
    // ============================================================
    @Transactional
    public void removerAluno(Long idAluno) {
        Usuario aluno = usuarioRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.setPersonalId(null);
        usuarioRepository.save(aluno);
    }


    // ============================================================
    // CONVERSÃO PARA DTO
    // ============================================================
    private AlunoResponse toAlunoResponse(Usuario usuario) {
        return AlunoResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .cidade(usuario.getCidade())
                .build();
    }
}
