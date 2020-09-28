package br.gov.ma.ssp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.MensagemNotificacaoLink;
import br.gov.ma.ssp.repository.MensagemNotificacaoLinkRepository;

@Service
public class MensagemNotificacaoLinkService {
	
	@Autowired
	private MensagemNotificacaoLinkRepository mensagemNotificacaoLinkRepository;
	
	public void salvar(MensagemNotificacaoLink mensagemNotLink){
		mensagemNotificacaoLinkRepository.save(mensagemNotLink);
	}
	
	public void deletar(MensagemNotificacaoLink mensagemNotLink){
		mensagemNotificacaoLinkRepository.delete(mensagemNotLink);
	}
	
	public MensagemNotificacaoLink pesquisarPorId1(MensagemNotificacaoLink mensagemNotLink){
		return mensagemNotificacaoLinkRepository.getOne(mensagemNotLink.getId());
	}
}
