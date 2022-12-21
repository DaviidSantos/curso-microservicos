package io.github.daviidsantos.msavaliadordecredito.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daviidsantos.msavaliadordecredito.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) throws JsonProcessingException {
        var json = converterParaJson(dadosSolicitacaoEmissaoCartao);
        rabbitTemplate.convertAndSend(queue.getName(), json);
    }

    private String converterParaJson(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dadosSolicitacaoEmissaoCartao);
        return json;
    }
}
