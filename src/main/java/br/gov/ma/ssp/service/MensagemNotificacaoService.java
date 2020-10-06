package br.gov.ma.ssp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.MensagemNotificacao;
import br.gov.ma.ssp.model.MensagemNotificacaoFuncionario;
import br.gov.ma.ssp.model.MensagemNotificacaoLink;
import br.gov.ma.ssp.model.MensagemNotificacaoUnidade;
import br.gov.ma.ssp.model.MensagemNotificacaoVisualizada;
import br.gov.ma.ssp.model.TipoMensagemNotificacao;
import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.model.dto.MensagemLinkDto;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoDto;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoVisualizarDto;
import br.gov.ma.ssp.repository.MensagemNotificacaoRepository;

@Service
public class MensagemNotificacaoService {
	@Autowired
	private MensagemNotificacaoRepository mensagemNotificacaoRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private TipoMensagemNotificacaoService tipoMensagemService;
	
	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private MensagemNotificacaoUnidadeService mensagemNotificacaoUnidadeService;
	
	@Autowired
	private MensagemNotificacaoLinkService mensagemNotificacaoLinkService;
	
	@Autowired
	private MensagemNotificacaoFuncionarioService mensagemNotificacaoFuncionarioService;
	
	@Autowired
	private MensagemNotificacaoVisualizadaService mensagemNotificacaoVisualizadaService;
	
	public HashMap<String, String> salvar(MensagemNotificacaoDto dto){
		Optional<MensagemNotificacaoDto> optionaldto = Optional.ofNullable(dto);
		HashMap<String, String> resultado = new HashMap<>();
		
	
		//validando as datas, que uando for uma mensagem que tem validade deve ter os periodos especificados corretamente
		// data inicio nao pode ser inferior a data de hoje
		//data fim nao pode ser inferior a data de inicio
		if(dto.getTemValidade()!=null && dto.getTemValidade() == true){
			if(dto.getDataValidadeInicio() !=null && dto.getDataValidadeFim()!=null){
				Date hoje = new Date();
				if(dto.getDataValidadeInicio().before(hoje) && dto.getDataValidadeFim().before(dto.getDataValidadeInicio())){
					resultado.put("mensagemErroDataExpirar", "A data informada e invalida!");
				}
			}
		}
		
		
		
		
		//validando se a mensagem informada possui uma texto, se existe um criador, e se tem um tipo valido, informado no dto e no banco!
		if(optionaldto.isPresent() 
				&& !Optional.ofNullable(dto.getDescricao()).orElse("").trim().isEmpty()
				&& dto.getFuncionarioCriador()!=null 
				&& dto.getTipoMensagem()!=null){
			Funcionario funcionario  = funcionarioService.pesquisarPorId(dto.getFuncionarioCriador());
			TipoMensagemNotificacao tipoMensagem = tipoMensagemService.pesquisaPorId(dto.getTipoMensagem());
			if(!Optional.ofNullable(funcionario).isPresent() || !Optional.ofNullable(tipoMensagem).isPresent()){
			
				if(!Optional.ofNullable(tipoMensagem).isPresent()) {
					resultado.put("mensagemErroTipoMensagem", "O tipo de Mensagem Informado  e invalido!");
				}
				if(!Optional.ofNullable(funcionario).isPresent()) {
					resultado.put("mensagemErroFuncionarioCriador", "O Criador informado e invalido !");
				}
				
				
			}else {
				MensagemNotificacao mensagemNotificacao = new MensagemNotificacao();
				getMensagemNotificacao(dto, funcionario, tipoMensagem, mensagemNotificacao);
				mensagemNotificacao = mensagemNotificacaoRepository.save(mensagemNotificacao);
				//verificando se o objeto foi salvo com sucesso
				//verificando se e do tipo unidade para salvar a unidade destinataria!
				if(Optional.ofNullable(mensagemNotificacao.getId()).isPresent()) {
					notificacaoUnidade(dto, mensagemNotificacao);
					notificacaoFuncionario(dto, mensagemNotificacao);
					notificacaoLink(dto, mensagemNotificacao);
					
					resultado.put("mensagemSucessoNotificacao", "Notificacao Criada com sucesso");
				}
			}

			
		}else {
			if(Optional.ofNullable(dto.getDescricao()).orElse("").trim().isEmpty()) {
				resultado.put("mensagemErroNotificacao", "Notificacao nao informada");
			}
			if(!Optional.ofNullable(dto.getFuncionarioCriador()).isPresent()) {
				resultado.put("mensagemErroFuncionarioCriador", "O Criador informado e invalido !");
			}
			if(!Optional.ofNullable(dto.getFuncionarioCriador()).isPresent()) {
				resultado.put("mensagemErroTipoMensagem", "O tipo de Mensagem Informado  e invalido!");
			}
			
		}
		
		
		return resultado;	
		
	}

	private void notificacaoLink(MensagemNotificacaoDto dto,
			MensagemNotificacao mensagemNotificacao) {
		if(Optional.ofNullable(dto.getListaLink()).orElse(new ArrayList<>()).isEmpty()) {
			for (MensagemLinkDto linkString : dto.getListaLink()) {
				MensagemNotificacaoLink link = new MensagemNotificacaoLink();
				link.setAtivo(true);
				link.setDataCricao(new Date());
				link.setFuncionarioCriador(mensagemNotificacao.getFuncionarioCriador());
				link.setLink(linkString.getLink());
				link.setMensagem(mensagemNotificacao);
				link.setTitulo(linkString.getTitulo());
				mensagemNotificacaoLinkService.salvar(link);
			}
		}
	}

	private void getMensagemNotificacao(MensagemNotificacaoDto dto, Funcionario funcionario,
			TipoMensagemNotificacao tipoMensagem, MensagemNotificacao mensagemNotificacao) {
		mensagemNotificacao.setRestricaoForca(dto.getRestricaoForca());
		mensagemNotificacao.setTipoMensagem(tipoMensagem);
		mensagemNotificacao.setFuncionarioCriador(funcionario);
		mensagemNotificacao.setEhUnidade(dto.getEhUnidade());
		mensagemNotificacao.setEhFuncionario(dto.getEhFuncionario());
		mensagemNotificacao.setDataValidadeInicio(dto.getDataValidadeInicio());
		mensagemNotificacao.setDataValidadeFim(dto.getDataValidadeFim());
		mensagemNotificacao.setDescricao(dto.getDescricao());
		mensagemNotificacao.setDataCriacao(new Date());
		mensagemNotificacao.setAtivo(true);
		mensagemNotificacao.setAddUnidFilha(dto.getAddUnidadeFilha());
		mensagemNotificacao.setId(dto.getId());
	}

	private boolean notificacaoFuncionario(MensagemNotificacaoDto dto,
			MensagemNotificacao mensagemNotificacao) {
		if(Optional.ofNullable(dto.getEhFuncionario()).isPresent() && dto.getEhFuncionario() == true && !Optional.ofNullable(dto.getFuncionarioDestinatario()).orElse(new ArrayList<>()).isEmpty()) {
			
			for (Integer idFuncionario : dto.getFuncionarioDestinatario()) {
				MensagemNotificacaoFuncionario msgFuncionario = new MensagemNotificacaoFuncionario();
				msgFuncionario.setAtivo(true);
				msgFuncionario.setFuncionarioCriador(mensagemNotificacao.getFuncionarioCriador());
				msgFuncionario.setFuncionarioDestinatario(funcionarioService.pesquisarPorId(idFuncionario));
				msgFuncionario.setMensagem(mensagemNotificacao);
				mensagemNotificacaoFuncionarioService.salvar(msgFuncionario);
			}
			
			return true;
		}
		return false;
	}

	private boolean notificacaoUnidade(MensagemNotificacaoDto dto,MensagemNotificacao mensagemNotificacao) {
		if(Optional.ofNullable(mensagemNotificacao).isPresent() 
				&& Optional.ofNullable(dto).isPresent() 
				&& Optional.ofNullable(dto.getEhUnidade()).isPresent() 
				&&  dto.getEhUnidade()== true && Optional.ofNullable(dto.getUnidadeDestinatario()).isPresent()) {
			
			//validacao para saber se e uma mensagem para uma unidade especifica,
			//verificando se a unidade foi informada no dto. e se existe no banco!
			Unidade unidadeDestinatario = null;
			unidadeDestinatario = unidadeService.findOneUnidade(dto.getUnidadeDestinatario());
			if(unidadeDestinatario==null) {
					return false;
			}
			
			//instanciando um objeto da tabela de notificacao de unidade
			MensagemNotificacaoUnidade msgUnidade = new MensagemNotificacaoUnidade();
			msgUnidade.setAtivo(true);
			msgUnidade.setDataCricao(new Date());
			msgUnidade.setFuncionario(mensagemNotificacao.getFuncionarioCriador());
			msgUnidade.setMensagem(mensagemNotificacao);
			msgUnidade.setUnidade(unidadeDestinatario);
			//salvando o objeto
			mensagemNotificacaoUnidadeService.salvar(msgUnidade);	
			
			mensagemUnidadeFilha(dto, mensagemNotificacao);
			
			return true;
		}
		return false;
	}

	private void mensagemUnidadeFilha(MensagemNotificacaoDto dto, MensagemNotificacao mensagemNotificacao) {
		if(Optional.ofNullable(dto.getAddUnidadeFilha()).isPresent() && dto.getAddUnidadeFilha()==true) {
			List<Unidade> unidadesFilhas = Optional.ofNullable(unidadeService.getUnidadesFilhas(dto.getUnidadeDestinatario())).orElse(new ArrayList<>());
			if(!unidadesFilhas.isEmpty()) {
				for (Unidade unidade : unidadesFilhas) {
					MensagemNotificacaoUnidade msgUnidade = new MensagemNotificacaoUnidade();
					msgUnidade.setAtivo(true);
					msgUnidade.setDataCricao(new Date());
					msgUnidade.setFuncionario(mensagemNotificacao.getFuncionarioCriador());
					msgUnidade.setMensagem(mensagemNotificacao);
					msgUnidade.setUnidade(unidade);
					//salvando o objeto
					mensagemNotificacaoUnidadeService.salvar(msgUnidade);
				}
			}
		}
	}
	
	public void deletar(MensagemNotificacao mensagem){
		mensagemNotificacaoRepository.delete(mensagem);
	}
	
	public MensagemNotificacao pesquisarPorId(MensagemNotificacao mensagem){
		return mensagemNotificacaoRepository.getOne(mensagem.getId());
	}
	
	public List<MensagemNotificacao> pesquisarTodasMensagens(){
		return mensagemNotificacaoRepository.findAll();
	}
	
	public List<MensagemNotificacaoVisualizarDto> getMensagensFuncionario(Funcionario funcionario){
		List<MensagemNotificacaoUnidade> notificacoesUnidade = mensagemNotificacaoUnidadeService.pesquisarPorUnidadeFuncionario(funcionario.getUnidade());
		List<MensagemNotificacaoFuncionario> notificacaoFuncioanrio = mensagemNotificacaoFuncionarioService.pesquisarFuncioanario(funcionario);
		
		List<MensagemNotificacao> msg = new ArrayList<>();
		List<MensagemNotificacao> msgUnidade = notificacoesUnidade.stream()
				.filter(nu-> validaMensagem(nu.getMensagem())==true )
				.map(MensagemNotificacaoUnidade::getMensagem).distinct().collect(Collectors.toList());
		List<MensagemNotificacao> msgFuncionario = notificacaoFuncioanrio.stream()
				.filter(nf-> validaMensagem(nf.getMensagem())==true )
				.map(MensagemNotificacaoFuncionario::getMensagem).distinct().collect(Collectors.toList());
		msg.addAll(msgUnidade);
		msg.addAll(msgFuncionario);
		List<MensagemNotificacaoVisualizarDto> dto = new ArrayList<>();
		dto = msg.stream().map(this::entidadeParaDto).distinct().collect(Collectors.toList());
		return dto;
		
	}
	
	private boolean validaMensagem(MensagemNotificacao msg) {
		
		if(Optional.ofNullable(msg.getTemValidade()).isPresent() && msg.getTemValidade() ==true) {
			if(Optional.ofNullable(msg.getDataValidadeInicio()).isPresent() && Optional.ofNullable(msg.getDataValidadeFim()).isPresent()) {
				if(msg.getDataValidadeInicio().before(msg.getDataValidadeFim())) {
					return true;
				}
			}
			return false;
		}
		
		return true;
		
	}
	
	
	private MensagemNotificacaoVisualizarDto entidadeParaDto(MensagemNotificacao element) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE)
				.setFieldAccessLevel(AccessLevel.PRIVATE).setFieldMatchingEnabled(true);

		return modelMapper.map(element, MensagemNotificacaoVisualizarDto.class);
	}
	
	public HashMap<String, String> visualizar(MensagemNotificacaoDto dto){
		HashMap<String, String> resultado = new HashMap<>();
		if(Optional.ofNullable(dto).isPresent() && Optional.ofNullable(dto.getId()).isPresent()) {
			MensagemNotificacao mensagemNotificacao = mensagemNotificacaoRepository.getOne(dto.getId());
			if(Optional.ofNullable(mensagemNotificacao).isPresent()) {
				MensagemNotificacaoVisualizada visualizada = new MensagemNotificacaoVisualizada();
				visualizada.setDataVisualizacao(new Date());
				visualizada.setFuncionarioDestinatario(funcionarioService.pesquisarPorId(dto.getFuncionarioCriador()));
				visualizada.setMensagem(mensagemNotificacao);
				mensagemNotificacaoVisualizadaService.salvar(visualizada);
			}
			resultado.put("mensagemSucessoVisualizar", "Visualizado com sucesso");
			return resultado;
		}
		
		resultado.put("mensagemErroVisualizar", "A Mensagem Informado  e invalido!");
		return resultado;
		
	}
}

