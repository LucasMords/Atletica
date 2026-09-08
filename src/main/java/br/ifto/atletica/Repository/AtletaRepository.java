package br.ifto.atletica.Repository;

import br.ifto.atletica.Entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    List<Atleta> findByModalidade_IdOrderByNomeAsc(Long modalidadeId);

    List<Atleta> findAllByOrderByNomeAsc();

}
