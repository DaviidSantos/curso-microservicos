package io.github.daviidsantos.msavaliadordecredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;
}
