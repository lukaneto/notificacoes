package br.gov.ma.ssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.MensagemNotificacaoVisualizada;

@Repository
public interface MensagemNotificacaoVisualizadaRepository extends JpaRepository<MensagemNotificacaoVisualizada, Integer>{

	List<MensagemNotificacaoVisualizada> findByFuncionarioDestinatarioAndMensagemId(Funcionario funcionario, Integer mensagem);
	
	
}
