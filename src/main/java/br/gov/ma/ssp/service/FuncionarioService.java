package br.gov.ma.ssp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository; 
	
	@Autowired
	private UnidadeService unidadeService;
	
	public void salvar(Funcionario funcionario) {
//		Optional<Funcionario> optionlaFUnc = Optional.ofNullable(funcionario);
//		
//		if(optionlaFUnc.isPresent() && !Optional.ofNullable(funcionario.getNome()).orElse("").isEmpty()) {
//			funcionarioRepository.save(funcionario);
//		}
		
		if(funcionario != null && funcionario.getNome() != null && !funcionario.getNome().trim().isEmpty()) {
			if(funcionario.getUnidade()!=null) {
				Unidade unidade = unidadeService.pesquisarPorId(funcionario.getUnidade());
				if(unidade!=null){
					funcionarioRepository.save(funcionario);
				}	
			}
		}
		
	}
	
	
	public void deletar(Funcionario funcionario) {
		funcionarioRepository.delete(funcionario);
	}
	
//	public Funcionario pesquisarPorId(Funcionario funcionario) {
//		//funcionarioRepository.findById(idFuncionario).get();
//		return funcionarioRepository.getOne(funcionario.getId());
//	}
//	
	
	public Funcionario pesquisarPorId(Funcionario idFuncionario) {
		return funcionarioRepository.getOne(idFuncionario.getId());
	}
	
	public Funcionario pesquisarPorId(Integer idFuncionario) {
		return funcionarioRepository.getOne(idFuncionario);		
	}
	
	 public List<Funcionario> pesquisarTodosFuncionarios(){
		 
		 return funcionarioRepository.findAll(); 
	 } 	 
	
}
