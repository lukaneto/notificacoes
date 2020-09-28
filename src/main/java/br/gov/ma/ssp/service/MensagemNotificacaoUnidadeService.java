package br.gov.ma.ssp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.MensagemNotificacaoUnidade;
import br.gov.ma.ssp.repository.MensagemNotificacaoUnidadeRepository;

@Service
public class MensagemNotificacaoUnidadeService {
	
	@Autowired
	private MensagemNotificacaoUnidadeRepository mensagemNotificacaoUnidadeRepository;
	
	public void salvar(MensagemNotificacaoUnidade mensagemNotUnidade){
		mensagemNotificacaoUnidadeRepository.save(mensagemNotUnidade);
	}
}
