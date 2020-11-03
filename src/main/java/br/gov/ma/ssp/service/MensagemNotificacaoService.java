package br.gov.ma.ssp.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.MensagemNotificacao;
import br.gov.ma.ssp.model.MensagemNotificacaoFuncionario;
import br.gov.ma.ssp.model.MensagemNotificacaoImagem;
import br.gov.ma.ssp.model.MensagemNotificacaoLink;
import br.gov.ma.ssp.model.MensagemNotificacaoUnidade;
import br.gov.ma.ssp.model.MensagemNotificacaoVisualizada;
import br.gov.ma.ssp.model.TipoMensagemNotificacao;
import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.model.dto.FileNotificacaoDto;
import br.gov.ma.ssp.model.dto.MensagemLinkDto;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoDto;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoVisualizarDto;
import br.gov.ma.ssp.model.dto.PaginaNotificacaoDto;
import br.gov.ma.ssp.repository.MensagemNotificacaoRepository;
import br.gov.ma.ssp.repository.MensagemNotificacaoVisualizadaRepository;

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
	
	@Autowired
	private FileUploadMensagemNotificacaoService fileUploadService;
	
	@Autowired
	private MensagemNotificacaoImagemService mensagemNotificacaoImagemService;
	
	
	
	public HashMap<String, String> salvar(MensagemNotificacaoDto dto){
		Optional<MensagemNotificacaoDto> optionaldto = Optional.ofNullable(dto);
		HashMap<String, String> resultado = new HashMap<>();
		dto.setEhUnidade(dto.getEhUnidade()==null?true:dto.getEhUnidade());
		if(Optional.ofNullable(dto.getTitulo()).orElse("").trim().isEmpty() 
				|| dto.getTitulo().trim().length() > 49) {
			resultado.put("mensagemErroTitulo", "O titulo informado  e invalido!");
		}
	
		//validando as datas, que uando for uma mensagem que tem validade deve ter os periodos especificados corretamente
		// data inicio nao pode ser inferior a data de hoje
		//data fim nao pode ser inferior a data de inicio
		if(dto.getTemValidade()!=null && dto.getTemValidade() == true){
			if(dto.getDataValidadeInicio() !=null && dto.getDataValidadeFim()!=null){
				Date hoje = new Date();
				if(dto.getDataValidadeInicio().after(hoje) || dto.getDataValidadeFim().after(dto.getDataValidadeInicio())){
					resultado.put("mensagemErroDataExpirar", "A data de fim deve ser superior a hoje, e a data de fim deve ser superior a data de inicio!");
				}
			}else {
				resultado.put("mensagemErroDataExpirar", "Verifique as datas de inicio e fim  informadas, pois são invalidas!");
			}
			
		}
		
		//validando se a mensagem informada possui uma texto, se existe um criador, e se tem um tipo valido, informado no dto e no banco!
		if(optionaldto.isPresent() 
				&& !Optional.ofNullable(dto.getDescricao()).orElse("").trim().isEmpty()
				&& dto.getFuncionarioCriador()!=null 
				&& dto.getTipoMensagem()!=null && dto.getTitulo()!=null && !dto.getTitulo().trim().isEmpty()
			){
			Funcionario funcionario  = funcionarioService.pesquisarPorId(dto.getFuncionarioCriador());
			TipoMensagemNotificacao tipoMensagem = tipoMensagemService.pesquisaPorId(dto.getTipoMensagem());
			if(!Optional.ofNullable(funcionario).isPresent() || !Optional.ofNullable(tipoMensagem).isPresent() || !resultado.isEmpty()){
			
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
					notificacaoImagem(dto, mensagemNotificacao);
					resultado.put("mensagemSucessoNotificacao", "Notificacao Criada com sucesso");
				}
			}

			
		}else {
			if(Optional.ofNullable(dto.getDescricao()).orElse("").trim().isEmpty()) {
				resultado.put("mensagemErroNotificacao", "Informe o texto da notificação!");
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

	private void notificacaoImagem(MensagemNotificacaoDto dto, MensagemNotificacao mensagemNotificacao) {
		if(Optional.ofNullable(dto.getListaImagem()).isPresent() && !dto.getListaImagem().isEmpty() ) {
			for (MultipartFile file : dto.getListaImagem()) {
				FileNotificacaoDto imagem = fileUploadService.salvarDocumento(file);
				
				if(Optional.ofNullable(imagem).isPresent()) {
					MensagemNotificacaoImagem mensagemNotImagem = new MensagemNotificacaoImagem();
					mensagemNotImagem.setAtivo(true);
					mensagemNotImagem.setDataCriacao(new Date());
					mensagemNotImagem.setFuncionarioCriador(mensagemNotificacao.getFuncionarioCriador());
					mensagemNotImagem.setMensagem(mensagemNotificacao);
					mensagemNotImagem.setDescricao(imagem.getNome());
					mensagemNotImagem.setImagem(imagem.getImagem());
					mensagemNotificacaoImagemService.salvar(mensagemNotImagem);
				}
				
			}
		}
	}

	private void notificacaoLink(MensagemNotificacaoDto dto,
			MensagemNotificacao mensagemNotificacao) {
		if(Optional.ofNullable(dto.getListaLink()).isPresent()) {
			for (MensagemLinkDto linkString : dto.getListaLink()) {
				if(!linkString.getTitulo().trim().isEmpty() && !linkString.getLink().trim().isEmpty()) {
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
		mensagemNotificacao.setTitulo(dto.getTitulo());
		mensagemNotificacao.setTemValidade(dto.getTemValidade());
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
		dto = msg.stream().map(this::entidadeParaDto)
				.sorted(Comparator.comparing(MensagemNotificacaoVisualizarDto::getDataCriacao).reversed())
				.distinct().collect(Collectors.toList());
		return dto;
		
	}
	
	
	public List<MensagemNotificacaoVisualizarDto> getMensagensFuncionarioPaginado(Funcionario funcionario,Pageable pageable){
		List<MensagemNotificacaoUnidade> notificacoesUnidade = mensagemNotificacaoUnidadeService.pesquisarPorUnidadeFuncionarioPaginado(funcionario.getUnidade(),pageable);
		List<MensagemNotificacaoFuncionario> notificacaoFuncioanrio = mensagemNotificacaoFuncionarioService.pesquisarFuncioanarioPaginado(funcionario,pageable);
		
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
		dto = msg.stream().map(this::entidadeParaDto)
				.sorted(Comparator.comparing(MensagemNotificacaoVisualizarDto::getDataCriacao).reversed())
				.distinct().collect(Collectors.toList());
		return dto;
		
	}
	
	private boolean validaMensagem(MensagemNotificacao msg) {
		
		if(Optional.ofNullable(msg.getTemValidade()).isPresent() && msg.getTemValidade() ==true) {
			if(Optional.ofNullable(msg.getDataValidadeInicio()).isPresent() && Optional.ofNullable(msg.getDataValidadeFim()).isPresent()) {
				if(msg.getDataValidadeInicio().before(msg.getDataValidadeFim()) && msg.getDataValidadeInicio().after(new Date())) {
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
	
	public PaginaNotificacaoDto getPagina(Pageable pageable, Funcionario funcionario) {
		
		PaginaNotificacaoDto pagina = new PaginaNotificacaoDto();
		
		
		List<MensagemNotificacaoVisualizarDto> listaTotal = getMensagensFuncionario(funcionario);
		Integer tamanho =listaTotal.size();
		List<MensagemNotificacaoVisualizarDto> listaPagina = getMensagensFuncionarioPaginado(funcionario,pageable);
		
		
		List<MensagemNotificacaoVisualizarDto> listaNaoVisualizada =  getListaNaoVisualizadas(listaTotal,funcionario);
		pagina.setPagina(pageable.getPageNumber());
		pagina.setQuantidadeNaoVisualizada(listaNaoVisualizada.size());
		pagina.setNotificacaoNaoVisualizada(listaNaoVisualizada(listaPagina,listaNaoVisualizada) );
		
		
		
		pagina.setQuantidadeTotalNotificacao(listaPagina.size());
		pagina.setMaxPagina(tamanho/pageable.getPageSize());		
	
		List<MensagemNotificacaoVisualizarDto> visualizada = getListaVisualizada(listaPagina, listaNaoVisualizada);
		
		
		pagina.setNotificacao(visualizada);
		pagina.setTodasNotificacoes(getAllLists(pagina.getNotificacao(),pagina.getNotificacaoNaoVisualizada()));
		
		return pagina;
		
	}

	private List<MensagemNotificacaoVisualizarDto> getListaVisualizada(
			List<MensagemNotificacaoVisualizarDto> listaPagina,
			List<MensagemNotificacaoVisualizarDto> listaNaoVisualizada) {
		List<MensagemNotificacaoVisualizarDto> visualizada = new ArrayList<MensagemNotificacaoVisualizarDto>();
		
		for (MensagemNotificacaoVisualizarDto mensagemNotificacaoVisualizarDto : listaPagina) {
			if(!listaNaoVisualizada.contains(mensagemNotificacaoVisualizarDto)) {
				mensagemNotificacaoVisualizarDto.setVisualizado(true);
				visualizada.add(mensagemNotificacaoVisualizarDto);
			}
		}
		return visualizada;
	}
	
	private List<MensagemNotificacaoVisualizarDto> listaNaoVisualizada(List<MensagemNotificacaoVisualizarDto> listaPagina,List<MensagemNotificacaoVisualizarDto> listaNaoVisualizada ) {
		List<MensagemNotificacaoVisualizarDto> resultado = new ArrayList<>();
		
		resultado = listaPagina.stream().filter(p-> listaNaoVisualizada.contains(p) )
				.collect(Collectors.toList());
		
		return resultado;
	}
	
	private  List<MensagemNotificacaoVisualizarDto> getAllLists(List<MensagemNotificacaoVisualizarDto> vista, List<MensagemNotificacaoVisualizarDto> naoVista){
		List<MensagemNotificacaoVisualizarDto> resultado = new ArrayList<>();
		resultado.addAll(naoVista);
		resultado.addAll(vista);
		return resultado;
		
	}
	
	
	private List<MensagemNotificacaoVisualizarDto> getListaNaoVisualizadas(List<MensagemNotificacaoVisualizarDto> mensagens, Funcionario funcionario){
		List<MensagemNotificacaoVisualizarDto> visualizada = new ArrayList<MensagemNotificacaoVisualizarDto>();
		for (MensagemNotificacaoVisualizarDto mensagemNotificacaoVisualizarDto : mensagens) {
			/**/
			List<MensagemNotificacaoVisualizada> lista = mensagemNotificacaoVisualizadaService.pesquisarPorFuncionarioEMensagem(funcionario,mensagemNotificacaoVisualizarDto.getId());
			if(!Optional.ofNullable(lista).isPresent() ||  Optional.ofNullable(lista).orElse(new ArrayList<>()).isEmpty()) {
				visualizada.add(mensagemNotificacaoVisualizarDto);
			}
		}
		
		
		return visualizada;
	}


	public boolean visualizar (MensagemNotificacao mensagem, Funcionario funcionario) {
		
		List<MensagemNotificacaoVisualizada> lista = mensagemNotificacaoVisualizadaService.pesquisarPorFuncionarioEMensagem(funcionario,mensagem.getId());
		if(!Optional.ofNullable(lista).isPresent() || lista.isEmpty() ) {
			mensagem = mensagemNotificacaoRepository.getOne(mensagem.getId());
			MensagemNotificacaoVisualizada mensagemNotVisualizada = new MensagemNotificacaoVisualizada();
			mensagemNotVisualizada.setDataVisualizacao(new Date());
			mensagemNotVisualizada.setFuncionarioDestinatario(funcionario);
			mensagemNotVisualizada.setMensagem(mensagem);
			mensagemNotificacaoVisualizadaService.salvar(mensagemNotVisualizada);
			return true;
		}
		
		return false;
	}

}

