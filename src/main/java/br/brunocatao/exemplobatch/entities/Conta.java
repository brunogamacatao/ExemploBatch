package br.brunocatao.exemplobatch.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Conta implements Serializable {
  private static final long serialVersionUID = -9024093602027903019L;

  @Id
  @GeneratedValue
  private Long id;
  private String numero;
  private double saldo;
  @ManyToOne(fetch = FetchType.EAGER)
  private Cliente cliente;
}
