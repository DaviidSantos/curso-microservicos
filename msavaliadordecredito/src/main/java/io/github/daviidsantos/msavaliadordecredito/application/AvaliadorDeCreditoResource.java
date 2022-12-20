package io.github.daviidsantos.msavaliadordecredito.application;

import io.github.daviidsantos.msavaliadordecredito.application.ex.DadosClienteNotFoundException;
import io.github.daviidsantos.msavaliadordecredito.application.ex.ErroComunicacaoMicroservicesException;
import io.github.daviidsantos.msavaliadordecredito.domain.model.CartaoCliente;
import io.github.daviidsantos.msavaliadordecredito.domain.model.DadosAvaliacao;
import io.github.daviidsantos.msavaliadordecredito.domain.model.ResponseAvaliacaoCliente;
import io.github.daviidsantos.msavaliadordecredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorDeCreditoResource {

    private final AvaliadorDeCreditoService avaliadorDeCreditoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @GetMapping(value = "situacao-cliete" ,params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){
        try {
            SituacaoCliente situacao = avaliadorDeCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.status(HttpStatus.OK).body(situacao);
        } catch (DadosClienteNotFoundException e) {
           return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dadosAvaliacao){
        try {
            ResponseAvaliacaoCliente avaliacao = avaliadorDeCreditoService.realizarAvaliacao(dadosAvaliacao.getCpf(), dadosAvaliacao.getRenda());
            return ResponseEntity.ok(avaliacao);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

}
