package br.brunocatao.exemplobatch.controllers;

import br.brunocatao.exemplobatch.dao.ContaRepository;
import br.brunocatao.exemplobatch.entities.Conta;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("contas")
public class ContaController {
  private ContaRepository contaRepository;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<Conta> getContas() {
    return contaRepository.findAll();
  }
}
