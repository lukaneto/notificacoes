package br.gov.ma.ssp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ma.ssp.model.MensagemNotificacao;

@Repository
public interface MensagemNotificacaoRepository extends JpaRepository<MensagemNotificacao, Integer>{

}
