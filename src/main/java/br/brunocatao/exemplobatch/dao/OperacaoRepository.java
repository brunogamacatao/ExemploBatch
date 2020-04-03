package br.brunocatao.exemplobatch.dao;

import br.brunocatao.exemplobatch.entities.Operacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {
  @Query("SELECT o FROM Operacao o WHERE o.processada = FALSE ORDER BY o.dataCriacao")
  public Page<Operacao> findNaoProcessadas(Pageable pageable);
}
