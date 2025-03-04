package com.itaubanco.transacao_api.business.service;

import com.itaubanco.transacao_api.business.services.EstatisticasService;
import com.itaubanco.transacao_api.business.services.TransacaoService;
import com.itaubanco.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.itaubanco.transacao_api.controller.dtos.TransacaoRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticaServiceTest {

    @InjectMocks
    EstatisticasService estatisticasService;

    @Mock
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;
    EstatisticasResponseDTO estatisticas;

    @BeforeEach
    void setUp() {
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        estatisticas = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void calcularEstatisticasComSucesso() {
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.singletonList(transacao));

        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticas);
    }

    @Test
    void calcularEstatisticasComSucessoQuandoListavazia() {

        // Cria o objeto esperado com valores vazios
        EstatisticasResponseDTO estatisticaEsperada =
                new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);

        // Simula o retorno vazio do serviço
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.emptyList());

        // Executa o método que queremos testar
        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        // Verifica se o método foi chamado exatamente uma vez
        verify(transacaoService, times(1)).buscarTransacoes(60);

        // Compara o resultado com o esperado
        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticaEsperada);
    }
}
