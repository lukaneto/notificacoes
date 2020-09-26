package br.gov.ma.ssp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ma.ssp.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Integer>{
	
}
