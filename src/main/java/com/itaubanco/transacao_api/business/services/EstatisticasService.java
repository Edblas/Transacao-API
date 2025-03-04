package com.itaubanco.transacao_api.business.services;

import com.itaubanco.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.itaubanco.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    private final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticasTransacoes(Integer intervaloBusca) {
        log.info("Iniciada busca de estatísticas de transações pelo intervalo de {} segundos", intervaloBusca);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);

        if (transacoes.isEmpty()) {
            log.info("Nenhuma transação encontrada no intervalo de {} segundos", intervaloBusca);
            return new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        long start = System.currentTimeMillis();

        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream()
                .mapToDouble(TransacaoRequestDTO::valor)
                .summaryStatistics();

        long finish = System.currentTimeMillis();
        long tempoRequisicao = finish - start;
        log.info("Tempo de requisição: " + tempoRequisicao + "milissegundos");

        log.info("Estatísticas retornadas com sucesso");
        return new EstatisticasResponseDTO(
                estatisticasTransacoes.getCount(),
                estatisticasTransacoes.getSum(),
                estatisticasTransacoes.getAverage(),
                estatisticasTransacoes.getMin(),
                estatisticasTransacoes.getMax()
        );
    }
}
