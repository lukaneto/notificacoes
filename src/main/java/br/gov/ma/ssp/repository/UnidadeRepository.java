package br.gov.ma.ssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ma.ssp.model.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Integer>{
	List<Unidade> findByPaiId(Integer pai);
	
}
