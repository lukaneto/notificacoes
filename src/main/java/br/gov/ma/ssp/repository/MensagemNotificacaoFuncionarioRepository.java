package br.gov.ma.ssp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ma.ssp.model.MensagemNotificacaoFuncionario;

public interface MensagemNotificacaoFuncionarioRepository extends JpaRepository<MensagemNotificacaoFuncionario, Integer>{
	
}
