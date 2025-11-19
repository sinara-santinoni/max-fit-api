package com.maxfit.Controllers;

import com.maxfit.dto.request.TreinoRegistroRequest;
import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.DashboardTreinoResponse;
import com.maxfit.services.RegistroTreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/treinos")
@RequiredArgsConstructor
public class RegistroTreinoController {

    private final RegistroTreinoService registroTreinoService;

    // ====== REGISTRAR TREINO DO DIA ======
    // POST /api/treinos/registro
    @PostMapping("/registro")
    public ResponseEntity<ApiResponse<Void>> registrarTreino(
            @Valid @RequestBody TreinoRegistroRequest request) {

        registroTreinoService.registrarTreino(request);
        return ResponseEntity.ok(
                ApiResponse.success("Treino registrado com sucesso!")
        );
    }

    // ====== DASHBOARD TREINOS ======
    // GET /api/treinos/dashboard/{alunoId}
    @GetMapping("/dashboard/{alunoId}")
    public ResponseEntity<ApiResponse<DashboardTreinoResponse>> dashboard(
            @PathVariable Long alunoId) {

        DashboardTreinoResponse response = registroTreinoService.dashboard(alunoId);
        return ResponseEntity.ok(
                ApiResponse.success("Dashboard gerado com sucesso!", response)
        );
    }
}
