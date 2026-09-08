package br.ifto.atletica.Repository;

import br.ifto.atletica.Entity.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {
}
