package com.maxfit.Controllers;

import com.maxfit.dto.response.DicaResponse;
import com.maxfit.dto.response.ProfissionalSuporteResponse;
import com.maxfit.dto.response.TutorialResponse;
import com.maxfit.services.SuporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suporte")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SuporteController {

    private final SuporteService suporteService;

    // ============================================================
    // LISTAR PSICÓLOGOS POR CIDADE
    // GET /api/suporte/psicologos?cidade=Florianópolis
    // ============================================================
    @GetMapping("/psicologos")
    public ResponseEntity<List<ProfissionalSuporteResponse>> listarPsicologos(
            @RequestParam(required = false) String cidade) {

        log.info("GET /api/suporte/psicologos - cidade: {}", cidade);

        List<ProfissionalSuporteResponse> psicologos =
                suporteService.listarPsicologosPorCidade(cidade);

        return ResponseEntity.ok(psicologos);
    }

    // ============================================================
    // LISTAR NUTRICIONISTAS POR CIDADE
    // GET /api/suporte/nutricionistas?cidade=Florianópolis
    // ============================================================
    @GetMapping("/nutricionistas")
    public ResponseEntity<List<ProfissionalSuporteResponse>> listarNutricionistas(
            @RequestParam(required = false) String cidade) {

        log.info("GET /api/suporte/nutricionistas - cidade: {}", cidade);

        List<ProfissionalSuporteResponse> nutricionistas =
                suporteService.listarNutricionistasPorCidade(cidade);

        return ResponseEntity.ok(nutricionistas);
    }

    // ============================================================
    // LISTAR TUTORIAIS
    // GET /api/suporte/tutoriais
    // ============================================================
    @GetMapping("/tutoriais")
    public ResponseEntity<List<TutorialResponse>> listarTutoriais() {

        log.info("GET /api/suporte/tutoriais");

        List<TutorialResponse> tutoriais = suporteService.listarTutoriais();

        return ResponseEntity.ok(tutoriais);
    }

    // ============================================================
    // LISTAR DICAS
    // GET /api/suporte/dicas
    // ============================================================
    @GetMapping("/dicas")
    public ResponseEntity<List<DicaResponse>> listarDicas() {

        log.info("GET /api/suporte/dicas");

        List<DicaResponse> dicas = suporteService.listarDicas();

        return ResponseEntity.ok(dicas);
    }

    // ============================================================
    // BUSCAR DICA POR ID
    // GET /api/suporte/dicas/{id}
    // ============================================================
    @GetMapping("/dicas/{id}")
    public ResponseEntity<DicaResponse> buscarDica(@PathVariable Long id) {

        log.info("GET /api/suporte/dicas/{} - buscar dica", id);

        DicaResponse dica = suporteService.buscarDicaPorId(id);

        if (dica == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dica);
    }
}
