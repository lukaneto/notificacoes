package br.gov.ma.ssp.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ma.ssp.model.MensagemNotificacaoUnidade;
import br.gov.ma.ssp.model.Unidade;

public interface MensagemNotificacaoUnidadeRepository extends JpaRepository<MensagemNotificacaoUnidade, Integer>{

	Page<MensagemNotificacaoUnidade> findByUnidadeAndMensagemAtivoIsTrue(Unidade unidade, Pageable page);
	
	List<MensagemNotificacaoUnidade> findByUnidadeAndMensagemAtivoIsTrue(Unidade unidade);

}
