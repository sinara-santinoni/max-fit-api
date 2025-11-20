package com.maxfit.Controllers;

import com.maxfit.dto.request.VincularAlunoRequest;
import com.maxfit.dto.response.AlunoResponse;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.services.PersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    // ====== LISTAR ALUNOS DO PERSONAL ======
    @GetMapping("/{personalId}/alunos")
    public ResponseEntity<ApiResponse<List<AlunoResponse>>> listarAlunos(
            @PathVariable Long personalId
    ) {
        List<AlunoResponse> alunos = personalService.listarAlunosDoPersonal(personalId);

        ApiResponse<List<AlunoResponse>> response =
                ApiResponse.success("Alunos do personal listados com sucesso", alunos);

        return ResponseEntity.ok(response);
    }

    // ====== LISTAR ALUNOS DISPONÍVEIS (SEM PERSONAL) ======
    @GetMapping("/alunos-disponiveis")
    public ResponseEntity<ApiResponse<List<AlunoResponse>>> listarAlunosDisponiveis() {
        List<AlunoResponse> alunos = personalService.listarAlunosDisponiveis();

        ApiResponse<List<AlunoResponse>> response =
                ApiResponse.success("Alunos disponíveis listados com sucesso", alunos);

        return ResponseEntity.ok(response);
    }

    // ====== VINCULAR ALUNO A UM PERSONAL ======
    @PostMapping("/vincular-aluno")
    public ResponseEntity<ApiResponse<AlunoResponse>> vincularAluno(
            @Valid @RequestBody VincularAlunoRequest request
    ) {
        AlunoResponse aluno = personalService.vincularAluno(request);

        ApiResponse<AlunoResponse> response =
                ApiResponse.success("Aluno vinculado ao personal com sucesso", aluno);

        return ResponseEntity.ok(response);
    }
}
