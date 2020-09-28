package br.gov.ma.ssp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.TipoMensagemNotificacao;
import br.gov.ma.ssp.repository.TipoMensagemNotificacaoRepository;

@Service
public class TipoMensagemNotificacaoService {
	
	@Autowired
	private TipoMensagemNotificacaoRepository tipoMensagemNotificacaoRepository;
	
	public void salve(TipoMensagemNotificacao tipoMensagemNot){
		tipoMensagemNotificacaoRepository.save(tipoMensagemNot);
	}
	
	public void delete(TipoMensagemNotificacao tipoMensagemNot){
		tipoMensagemNotificacaoRepository.delete(tipoMensagemNot);
	}
	
	public TipoMensagemNotificacao pesquisaPorId(TipoMensagemNotificacao tipoMensagemNotificacao){
		return tipoMensagemNotificacaoRepository.getOne(tipoMensagemNotificacao.getId());
	}
	
	public TipoMensagemNotificacao pesquisaPorId(Integer tipoMensagemNotificacao){
		return tipoMensagemNotificacaoRepository.getOne(tipoMensagemNotificacao);
	}
	
	public List<TipoMensagemNotificacao> pesquisaTodosTipos(){
		return tipoMensagemNotificacaoRepository.findAll();
	}
}
