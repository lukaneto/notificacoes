package br.gov.ma.ssp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ma.ssp.model.MensagemNotificacaoLink;

public interface MensagemNotificacaoUnidadeRepository extends JpaRepository<MensagemNotificacaoLink, Integer>{

}
