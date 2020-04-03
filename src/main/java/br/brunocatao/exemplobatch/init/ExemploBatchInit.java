package br.brunocatao.exemplobatch.init;

import br.brunocatao.exemplobatch.dao.ClienteRepository;
import br.brunocatao.exemplobatch.dao.ContaRepository;
import br.brunocatao.exemplobatch.entities.Cliente;
import br.brunocatao.exemplobatch.entities.Conta;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExemploBatchInit implements InitializingBean {
  private static final Logger log = LoggerFactory.getLogger(ExemploBatchInit.class);

  private ContaRepository contaRepository;
  private ClienteRepository clienteRepository;

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("Verificando se o banco de dados já está populado ...");
    if (!contaRepository.findAll().isEmpty()) {
      log.info("O banco já contem dados");
      return;
    }

    log.info("O banco ainda não contem dados, criando ...");
    createConta("111111", "Fulano", 150.0);
    createConta("222222", "Cicrano", 250.0);
    createConta("333333", "Beltrano", 350.0);
    createConta("444444", "Maria", 450.0);
    createConta("555555", "Ana", 550.0);
    log.info("Pronto");
  }

  private void createConta(String numero, String nomeCliente, double saldo) {
    Cliente cliente = new Cliente();
    cliente.setNome(nomeCliente);
    cliente = clienteRepository.save(cliente);

    Conta conta = new Conta();
    conta.setNumero(numero);
    conta.setSaldo(saldo);
    conta.setCliente(cliente);
    contaRepository.save(conta);
  }
}
