package br.gov.ma.ssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ma.ssp.model.MensagemNotificacaoUnidade;
import br.gov.ma.ssp.model.Unidade;

public interface MensagemNotificacaoUnidadeRepository extends JpaRepository<MensagemNotificacaoUnidade, Integer>{

	List<MensagemNotificacaoUnidade> findByUnidadeAndMensagemAtivoIsTrue(Unidade unidade);

}
