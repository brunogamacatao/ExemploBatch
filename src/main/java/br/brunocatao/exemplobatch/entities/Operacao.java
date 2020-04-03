package br.brunocatao.exemplobatch.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Operacao implements Serializable {
  private static final long serialVersionUID = -6706701684294211782L;

  public enum TipoOperacao {
    SAQUE,
    DEPOSITO,
    TRANSFERENCIA
  }

  @Id
  @GeneratedValue
  private Long id;
  @Enumerated(EnumType.STRING)
  private TipoOperacao tipoOperacao;
  @ManyToOne
  private Conta origem;
  @ManyToOne
  private Conta destino;
  private double valor;
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataCriacao;
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataProcessamento;
  private boolean processada = false;
}
