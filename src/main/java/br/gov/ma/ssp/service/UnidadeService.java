package br.gov.ma.ssp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.repository.UnidadeRepository;

@Service
public class UnidadeService {
	private UnidadeRepository unidadeRepository;
	
	public void salvar(Unidade unidade) {
		unidadeRepository.save(unidade);
	}
	
	public void deletar(Unidade unidade) {
		unidadeRepository.delete(unidade);
	}
	
	public Unidade pesquisarPorId(Unidade unidade) {
		return unidadeRepository.getOne(unidade.getId());
	}
	

	public Unidade pesquisarPorId(Integer unidade) {
		return unidadeRepository.getOne(unidade);
	}
	
	public List<Unidade> pesquisarTodasUnidades(){
		return unidadeRepository.findAll();
	}
}