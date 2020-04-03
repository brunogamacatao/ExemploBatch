package br.brunocatao.exemplobatch.controllers;

import br.brunocatao.exemplobatch.dao.ContaRepository;
import br.brunocatao.exemplobatch.dao.OperacaoRepository;
import br.brunocatao.exemplobatch.entities.Conta;
import br.brunocatao.exemplobatch.entities.Operacao;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("operacoes")
public class OperacaoController {
  private ContaRepository contaRepository;
  private OperacaoRepository operacaoRepository;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<Operacao> getOperacoes() {
    return operacaoRepository.findAll();
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/gera", method = RequestMethod.GET)
  public String geraOperacoesAleatorias() {
    List<Conta> contas = contaRepository.findAll();
    Operacao.TipoOperacao tipos[] = {
        Operacao.TipoOperacao.SAQUE,
        Operacao.TipoOperacao.DEPOSITO,
        Operacao.TipoOperacao.TRANSFERENCIA
    };

    for (int i = 0; i < 10; i++) {
      // embaralha a lista
      Collections.shuffle(contas);

      // pega as duas primeiras contas
      Conta c1 = contas.get(0);
      Conta c2 = contas.get(1);

      // cria uma operacao
      Operacao op = new Operacao();
      op.setOrigem(c1);
      op.setDestino(c2);
      op.setValor(Math.random() * 100.0);
      op.setTipoOperacao(tipos[(int)(Math.random() * tipos.length)]);

      // salva a operacao
      operacaoRepository.save(op);
    }

    return "ok";
  }
}
