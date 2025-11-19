package com.maxfit.Controllers;

import com.maxfit.dto.response.ApiResponse;
import com.maxfit.dto.response.ProfissionalSuporteResponse;
import com.maxfit.services.SuporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suporte")
@RequiredArgsConstructor
public class SuporteController {

    private final SuporteService suporteService;

    @GetMapping("/psicologos")
    public ResponseEntity<ApiResponse<List<ProfissionalSuporteResponse>>> listarPsicologos(
            @RequestParam(required = false) String cidade
    ) {
        List<ProfissionalSuporteResponse> lista =
                suporteService.listarPsicologosPorCidade(cidade);

        ApiResponse<List<ProfissionalSuporteResponse>> resp =
                ApiResponse.<List<ProfissionalSuporteResponse>>builder()
                        .sucesso(true)
                        .mensagem("Psicólogos encontrados")
                        .data(lista)
                        .build();

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/nutricionistas")
    public ResponseEntity<ApiResponse<List<ProfissionalSuporteResponse>>> listarNutricionistas(
            @RequestParam(required = false) String cidade
    ) {
        List<ProfissionalSuporteResponse> lista =
                suporteService.listarNutricionistasPorCidade(cidade);

        ApiResponse<List<ProfissionalSuporteResponse>> resp =
                ApiResponse.<List<ProfissionalSuporteResponse>>builder()
                        .sucesso(true)
                        .mensagem("Nutricionistas encontrados")
                        .data(lista)
                        .build();

        return ResponseEntity.ok(resp);
    }
}
