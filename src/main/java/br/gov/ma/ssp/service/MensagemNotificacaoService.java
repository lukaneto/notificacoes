package br.gov.ma.ssp.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.MensagemNotificacao;
import br.gov.ma.ssp.repository.MensagemNotificacaoRepository;

@Service
public class MensagemNotificacaoService implements IMensagemNotificacaoSerivce{
	
	@Autowired
	private MensagemNotificacaoRepository repository;

	
	public List<MensagemNotificacao> findAll() {
		// TODO Auto-generated method stub
		return (List <MensagemNotificacao>) repository.findAll();
	} 
	
	
}
