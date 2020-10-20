package br.gov.ma.ssp.repository;

import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.MensagemNotificacaoFuncionario;

public interface MensagemNotificacaoFuncionarioRepository extends JpaRepository<MensagemNotificacaoFuncionario, Integer>{

	List<MensagemNotificacaoFuncionario> findByFuncionarioDestinatarioAndMensagemAtivoIsTrue(Funcionario funcionario);
	
	Page<MensagemNotificacaoFuncionario> findByFuncionarioDestinatarioAndMensagemAtivoIsTrue(Funcionario funcionario,Pageable page);
	
}
