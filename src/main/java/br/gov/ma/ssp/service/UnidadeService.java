package br.gov.ma.ssp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.repository.UnidadeRepository;

@Service
public class UnidadeService {
	
	@Autowired
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
	

	public Unidade findOneUnidade(Integer unidade) {
		return unidadeRepository.getOne(unidade);
	}
	
	public List<Unidade> pesquisarTodasUnidades(){
		return Optional.ofNullable(unidadeRepository.findAll()).orElse(new ArrayList<>());
	}
	
	public List<Unidade> getUnidadesFilhas(Integer unidadePai) {
		return unidadeRepository.findByPaiId(unidadePai);
	}
}