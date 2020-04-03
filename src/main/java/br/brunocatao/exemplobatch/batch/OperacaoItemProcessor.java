package br.brunocatao.exemplobatch.batch;

import br.brunocatao.exemplobatch.entities.Conta;
import br.brunocatao.exemplobatch.entities.Operacao;
import org.springframework.batch.item.ItemProcessor;

public class OperacaoItemProcessor implements ItemProcessor<Operacao, Operacao> {
  @Override
  public Operacao process(Operacao operacao) throws Exception {
    switch(operacao.getTipoOperacao()) {
      case SAQUE:
        operacao.getOrigem().setSaldo(operacao.getOrigem().getSaldo() - operacao.getValor());
        break;
      case DEPOSITO:
        operacao.getOrigem().setSaldo(operacao.getOrigem().getSaldo() + operacao.getValor());
        break;
      case TRANSFERENCIA:
        operacao.getOrigem().setSaldo(operacao.getOrigem().getSaldo() - operacao.getValor());
        operacao.getDestino().setSaldo(operacao.getDestino().getSaldo() + operacao.getValor());
        break;
    }

    operacao.setProcessada(true);

    return operacao;
  }
}
