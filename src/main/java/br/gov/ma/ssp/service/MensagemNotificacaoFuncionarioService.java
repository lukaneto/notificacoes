package br.gov.ma.ssp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.MensagemNotificacaoFuncionario;
import br.gov.ma.ssp.repository.MensagemNotificacaoFuncionarioRepository;

@Service
public class MensagemNotificacaoFuncionarioService {
	
	@Autowired
	private MensagemNotificacaoFuncionarioRepository mensagemNotificacaoFuncionarioRepository;
	
	public void salvar(MensagemNotificacaoFuncionario mensagemNotFuncionario){
		mensagemNotificacaoFuncionarioRepository.save(mensagemNotFuncionario);
	}
	
	public void delete(MensagemNotificacaoFuncionario mensagemNotFuncionario){
		mensagemNotificacaoFuncionarioRepository.delete(mensagemNotFuncionario);
	}
	
	public MensagemNotificacaoFuncionario pesquisaPorId(MensagemNotificacaoFuncionario mensagemNotFuncionario){
		return mensagemNotificacaoFuncionarioRepository.getOne(mensagemNotFuncionario.getId());
	}
	
	public List<MensagemNotificacaoFuncionario> pesquisaTodosFuncionarios(){
		return mensagemNotificacaoFuncionarioRepository.findAll();
	}
}
