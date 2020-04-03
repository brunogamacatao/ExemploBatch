package br.brunocatao.exemplobatch.dao;

import br.brunocatao.exemplobatch.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
