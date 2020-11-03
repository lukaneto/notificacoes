package br.gov.ma.ssp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.MensagemNotificacaoVisualizada;
import br.gov.ma.ssp.repository.MensagemNotificacaoVisualizadaRepository;

@Service
public class MensagemNotificacaoVisualizadaService {
	
	@Autowired
	private MensagemNotificacaoVisualizadaRepository mensagemNotificacaoVisualizadaRepository;
	
	public void salvar(MensagemNotificacaoVisualizada mensagemNotVisualizada){
		mensagemNotificacaoVisualizadaRepository.save(mensagemNotVisualizada);
	}
	
	public void delete(MensagemNotificacaoVisualizada mensagemNotVisualizada){
		mensagemNotificacaoVisualizadaRepository.save(mensagemNotVisualizada);
	}
	
	public MensagemNotificacaoVisualizada pesquisaPeloId(MensagemNotificacaoVisualizada mensagemNotVizualisada){
		return mensagemNotificacaoVisualizadaRepository.getOne(mensagemNotVizualisada.getId());
	}
	
	public List<MensagemNotificacaoVisualizada> pesquisaTodosVisualizada(){
		return mensagemNotificacaoVisualizadaRepository.findAll();
	}

	public List<MensagemNotificacaoVisualizada> pesquisarPorFuncionarioEMensagem(Funcionario funcionario, Integer id) {
		return mensagemNotificacaoVisualizadaRepository
		.findByFuncionarioDestinatarioAndMensagemId(funcionario, id);
	}
	
	
}
