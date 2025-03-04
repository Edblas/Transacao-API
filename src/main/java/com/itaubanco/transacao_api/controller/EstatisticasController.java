package com.itaubanco.transacao_api.controller;
import com.itaubanco.transacao_api.business.services.EstatisticasService;
import com.itaubanco.transacao_api.controller.dtos.EstatisticasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
@Service
public class EstatisticasController {

    private final EstatisticasService estatisticasService;

    @GetMapping
    @Operation(description = "Endpoint responsavel por buscar estatisticas de transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de estatisticas de transações"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
            @RequestParam(value = "intervalorBusca", required = false, defaultValue = "60") Integer intervalorBusca){
      return ResponseEntity.ok(
              estatisticasService.calcularEstatisticasTransacoes(intervalorBusca));
    }

}
