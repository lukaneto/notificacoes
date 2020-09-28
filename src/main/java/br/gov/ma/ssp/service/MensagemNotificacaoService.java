package br.gov.ma.ssp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.MensagemNotificacao;
import br.gov.ma.ssp.model.MensagemNotificacaoUnidade;
import br.gov.ma.ssp.model.TipoMensagemNotificacao;
import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoDto;
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
	
	public Boolean salvar(MensagemNotificacaoDto dto){
		Optional<MensagemNotificacaoDto> optionaldto = Optional.ofNullable(dto);
		
		MensagemNotificacao mensagemNotificacao = new MensagemNotificacao();
	
		//validando as datas, que uando for uma mensagem que tem validade deve ter os periodos especificados corretamente
		// data inicio nao pode ser inferior a data de hoje
		//data fim nao pode ser inferior a data de inicio
		if(dto.getTemValidade()!=null && dto.getTemValidade() == true){
			if(dto.getDataValidadeInicio() !=null && dto.getDataValidadeFim()!=null){
				Date hoje = new Date();
				if(dto.getDataValidadeInicio().before(hoje) && dto.getDataValidadeFim().before(dto.getDataValidadeInicio())){
					return false;
				}
			}
		}
		
		//validacao para saber se e uma mensagem para uma unidade especifica,
		//verificando se a unidade foi informada no dto. e se existe no banco!
		Unidade unidadeDestinatario = null;
		if(dto.getEhUnidade()!=null && dto.getEhUnidade() == true && dto.getUnidadeDestinatario()!=null) {
			unidadeDestinatario = unidadeService.pesquisarPorId(dto.getUnidadeDestinatario());
			if(unidadeDestinatario==null) {
				return false;
			}
		}
		
		
		//validando se a mensagem informada possui uma texto, se existe um criador, e se tem um tipo valido, informado no dto e no banco!
		if(optionaldto.isPresent() && !Optional.ofNullable(dto.getDescricao()).orElse("").isEmpty() && dto.getFuncionarioCriador()!=null 
				&& dto.getTipoMensagem()!=null){
			Funcionario funcionario  = funcionarioService.pesquisarPorId(dto.getFuncionarioCriador());
			TipoMensagemNotificacao tipoMensagem = tipoMensagemService.pesquisaPorId(dto.getTipoMensagem());
			if(funcionario!=null && tipoMensagem!=null){
				dto.setDataCriacao(new Date());
				
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
				
			
				mensagemNotificacao = mensagemNotificacaoRepository.save(mensagemNotificacao);
				
				//verificando se o objeto foi salvo com sucesso
				//verificando se e do tipo unidade para salvar a unidade destinataria!
				if(mensagemNotificacao.getId()!=null) {
					if(dto.getEhUnidade()!=null &&  dto.getEhUnidade()==true && dto.getUnidadeDestinatario()!=null) {
						
							//instanciando um objeto da tabela de notificacao de unidade
							MensagemNotificacaoUnidade msgUnidade = new MensagemNotificacaoUnidade();
							msgUnidade.setAtivo(true);
							msgUnidade.setDataCricao(new Date());
							msgUnidade.setFuncionario(mensagemNotificacao.getFuncionarioCriador());
							msgUnidade.setMensagem(mensagemNotificacao);
							msgUnidade.setUnidade(unidadeDestinatario);
							//salvando o objeto
							mensagemNotificacaoUnidadeService.salvar(msgUnidade);	
							
							return true;
					}
					
				}
				
							
				
				return true;
				
				
			}else{
				return false;
			}
		}else{
			return false;
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
}

//======================================================================================================================
//package br.gov.ma.ssp.service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import br.gov.ma.ssp.model.Funcionario;
//import br.gov.ma.ssp.model.MensagemNotificacao;
//import br.gov.ma.ssp.model.TipoMensagemNotificacao;
//import br.gov.ma.ssp.repository.MensagemNotificacaoRepository;
//
//@Service
//public class MensagemNotificacaoService {
//	@Autowired
//	private MensagemNotificacaoRepository mensagemNotificacaoRepository;
//	
//	@Autowired
//	private FuncionarioService funcionarioService;
//	
//	@Autowired
//	private TipoMensagemNotificacaoService tipoMensagemService;
//	
//	public Boolean salvar(MensagemNotificacao mensagem){
//		Optional<MensagemNotificacao> optionalMensagemNotificacao = Optional.ofNullable(mensagem);
//		if(mensagem.getTemValidade()!=null && mensagem.getTemValidade() == true){
//			if(mensagem.getDataValidadeInicio() !=null && mensagem.getDataValidadeFim()!=null){
//				Date hoje = new Date();
//				if(mensagem.getDataValidadeInicio().before(hoje) && mensagem.getDataValidadeFim().before(mensagem.getDataValidadeInicio())){
//					return false;
//				}
//			}
//		}
//		if(optionalMensagemNotificacao.isPresent() && !Optional.ofNullable(mensagem.getDescricao()).orElse("").isEmpty() && mensagem.getFuncionarioCriador()!=null && mensagem.getTipoMensagem()!=null){
//			Funcionario funcionario  = funcionarioService.pesquisarPorId(mensagem.getFuncionarioCriador());
//			TipoMensagemNotificacao tipoMensagem = tipoMensagemService.pesquisaPorId(mensagem.getTipoMensagem());
//			if(funcionario!=null && tipoMensagem!=null){
//				mensagem.setDataCriacao(new Date());
//				mensagem = mensagemNotificacaoRepository.save(mensagem);
//			}else{
//				return false;
//			}
//		}else{
//			return false;
//		}
//		
//		
//		
//		
//		return true;
//		
//	}
//	
//	public void deletar(MensagemNotificacao mensagem){
//		mensagemNotificacaoRepository.delete(mensagem);
//	}
//	
//	public MensagemNotificacao pesquisarPorId(MensagemNotificacao mensagem){
//		return mensagemNotificacaoRepository.getOne(mensagem.getId());
//	}
//	
//	public List<MensagemNotificacao> pesquisarTodasMensagens(){
//		return mensagemNotificacaoRepository.findAll();
//	}
//}

