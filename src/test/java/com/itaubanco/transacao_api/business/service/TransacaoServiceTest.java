package com.itaubanco.transacao_api.business.service;

import com.itaubanco.transacao_api.business.services.EstatisticasService;
import com.itaubanco.transacao_api.business.services.TransacaoService;
import com.itaubanco.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.itaubanco.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.itaubanco.transacao_api.infracture.exceptions.UnprocessableEntity;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;
    EstatisticasResponseDTO estatisticas;

    @BeforeEach
    void setUp() {
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        estatisticas = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void deveAdicionarTransacaoComSucesso() {

        transacaoService.adicionarTransacoes(transacao);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);

        assertTrue(transacoes.contains(transacao));

    }

    @Test
    void deveLancarExcecaoCasoValorSejaNegativo() {

        UnprocessableEntity excpetion = assertThrows(UnprocessableEntity.class,
                () -> transacaoService.adicionarTransacoes((new TransacaoRequestDTO(-10.0, OffsetDateTime.now()))));

        assertEquals("Valor não pode ser menor que 0", excpetion.getMessage());
    }

    @Test
    void deveLancarExcecaoCasoDataOuHoraMaiorQueAtual() {

        UnprocessableEntity excpetion = assertThrows(UnprocessableEntity.class,
                () -> transacaoService.adicionarTransacoes((new TransacaoRequestDTO(-10.0, OffsetDateTime.now().plusDays(1)))));

        assertEquals("Data e hora maiores que a data e hora atuais", excpetion.getMessage());
    }

    @Test
    void deveLimparTransacaoComSucesso() {

        transacaoService.limparTransacoes();

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);

        assertTrue(transacoes.isEmpty());

    }

    @Test
    void deveBuscarTransacaoDentroDoIntervalo() {

        TransacaoRequestDTO dto = new TransacaoRequestDTO(10.00, OffsetDateTime.now().minusHours(1));

        transacaoService.adicionarTransacoes(transacao);
        transacaoService.adicionarTransacoes(dto);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(60);

        assertTrue(transacoes.contains(transacao));
        assertFalse(transacoes.contains(dto));

    }
}
