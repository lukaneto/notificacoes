package br.gov.ma.ssp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.MensagemNotificacaoImagem;
import br.gov.ma.ssp.repository.MensagemNotificacaoImagemRepository;

@Service
public class MensagemNotificacaoImagemService {
	
	@Autowired
	private MensagemNotificacaoImagemRepository mensagemNotificacaoImagemRepository;
	
	public void salvar(MensagemNotificacaoImagem mensagemNotImagem){
		mensagemNotificacaoImagemRepository.save(mensagemNotImagem);
	}
	
	public void deletar(MensagemNotificacaoImagem mensagemNotImagem){
		mensagemNotificacaoImagemRepository.delete(mensagemNotImagem);
	}
	
	public MensagemNotificacaoImagem pesquisaPorId(MensagemNotificacaoImagem mensagemNotImagem){
		return mensagemNotificacaoImagemRepository.getOne(mensagemNotImagem.getId());
	}
}
