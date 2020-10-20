package br.gov.ma.ssp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.MensagemNotificacaoUnidade;
import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.repository.MensagemNotificacaoUnidadeRepository;

@Service
public class MensagemNotificacaoUnidadeService {
	
	@Autowired
	private MensagemNotificacaoUnidadeRepository mensagemNotificacaoUnidadeRepository;
	
	public void salvar(MensagemNotificacaoUnidade mensagemNotUnidade){
		mensagemNotificacaoUnidadeRepository.save(mensagemNotUnidade);
	}

	public List<MensagemNotificacaoUnidade> pesquisarPorUnidadeFuncionario(Unidade unidade) {
		if(Optional.ofNullable(unidade).isPresent()) {
			return mensagemNotificacaoUnidadeRepository.findByUnidadeAndMensagemAtivoIsTrue(unidade);
		}
		return new ArrayList<>();
	}
	
	public List<MensagemNotificacaoUnidade> pesquisarPorUnidadeFuncionarioPaginado(Unidade unidade, Pageable page) {
		if(Optional.ofNullable(unidade).isPresent()) {
			
			Page<MensagemNotificacaoUnidade> pagina = mensagemNotificacaoUnidadeRepository.findByUnidadeAndMensagemAtivoIsTrue(unidade,page);
			
			if(Optional.ofNullable(pagina).isPresent()) {
				return pagina.getContent();
			}
			
		}
		return new ArrayList<>();
	}
}
