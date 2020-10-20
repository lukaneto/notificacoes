package br.gov.ma.ssp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.Funcionario;
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

	public List<MensagemNotificacaoFuncionario> pesquisarFuncioanario(Funcionario funcionario) {
		// TODO Auto-generated method stub
		return mensagemNotificacaoFuncionarioRepository.findByFuncionarioDestinatarioAndMensagemAtivoIsTrue(funcionario);
	}
	
	public List<MensagemNotificacaoFuncionario> pesquisarFuncioanarioPaginado(Funcionario funcionario,Pageable page) {
		Page<MensagemNotificacaoFuncionario> pagina =
				mensagemNotificacaoFuncionarioRepository.findByFuncionarioDestinatarioAndMensagemAtivoIsTrue(funcionario,page);
		if(Optional.ofNullable(pagina).isPresent()) {
			return  pagina.getContent();	
		}
		return new ArrayList<MensagemNotificacaoFuncionario>();
	}
}
