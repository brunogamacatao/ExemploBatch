package br.brunocatao.exemplobatch.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Cliente implements Serializable {
  private static final long serialVersionUID = 5443461790121579166L;

  @Id
  @GeneratedValue
  private Long id;
  private String nome;
  @JsonIgnore
  @OneToMany(mappedBy = "cliente")
  private List<Conta> contas;
}
