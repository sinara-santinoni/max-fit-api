package com.maxfit.services;

import com.maxfit.dto.request.VincularAlunoRequest;
import com.maxfit.dto.response.AlunoDoPersonalResponse;
import com.maxfit.model.TipoUsuario;
import com.maxfit.model.Usuario;
import com.maxfit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Lista todos os alunos vinculados a um personal (Tipo = ALUNO e personalId = ID do personal).
     */
    public List<AlunoDoPersonalResponse> listarAlunosDoPersonal(Long personalId) {

        Usuario personal = usuarioRepository.findById(personalId)
                .filter(u -> u.getTipo() == TipoUsuario.PERSONAL)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Personal não encontrado ou usuário não é do tipo PERSONAL."
                ));

        List<Usuario> alunos = usuarioRepository
                .findByTipoAndPersonalId(TipoUsuario.ALUNO, personal.getId());

        return alunos.stream()
                .map(this::toAlunoDoPersonalResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os alunos que ainda NÃO estão vinculados a nenhum personal.
     * (personalId IS NULL)
     */
    public List<AlunoDoPersonalResponse> listarAlunosDisponiveis() {

        List<Usuario> alunos = usuarioRepository
                .findByTipoAndPersonalIdIsNull(TipoUsuario.ALUNO);

        return alunos.stream()
                .map(this::toAlunoDoPersonalResponse)
                .collect(Collectors.toList());
    }

    /**
     * Vincula um aluno (TipoUsuario.ALUNO) a um personal (TipoUsuario.PERSONAL).
     */
    public AlunoDoPersonalResponse vincularAluno(VincularAlunoRequest request) {

        Usuario personal = usuarioRepository.findById(request.getPersonalId())
                .filter(u -> u.getTipo() == TipoUsuario.PERSONAL)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Personal inválido (não encontrado ou não é PERSONAL)."
                ));

        Usuario aluno = usuarioRepository.findById(request.getAlunoId())
                .filter(u -> u.getTipo() == TipoUsuario.ALUNO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aluno não encontrado ou tipo != ALUNO."
                ));

        // Faz o vínculo
        aluno.setPersonalId(personal.getId());
        usuarioRepository.save(aluno);

        return toAlunoDoPersonalResponse(aluno);
    }

    // ==========================================================
    // ======================   MAPPER   =========================
    // ==========================================================

    private AlunoDoPersonalResponse toAlunoDoPersonalResponse(Usuario aluno) {
        return AlunoDoPersonalResponse.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .cidade(aluno.getCidade())
                .personalId(aluno.getPersonalId())
                .criadoEm(aluno.getCriadoEm())
                .build();
    }
}
