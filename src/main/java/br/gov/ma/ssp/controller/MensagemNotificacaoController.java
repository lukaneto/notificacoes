package br.gov.ma.ssp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.ma.ssp.model.Funcionario;
import br.gov.ma.ssp.model.MensagemNotificacao;
import br.gov.ma.ssp.model.Unidade;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoDto;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoVisualizarDto;
import br.gov.ma.ssp.model.dto.PaginaNotificacaoDto;
import br.gov.ma.ssp.model.enumeration.TipoPesquisaPessoaFisica;
import br.gov.ma.ssp.service.MensagemNotificacaoService;
import br.gov.ma.ssp.service.TipoMensagemNotificacaoService;
import br.gov.ma.ssp.service.UnidadeService;


@Controller
@RequestMapping("/notificacao")
public class MensagemNotificacaoController {

	@Autowired
	private MensagemNotificacaoService mensagemNotificacaoService;
	
	@Autowired
	private TipoMensagemNotificacaoService tipoMensagemNotificacaoService;
	
	@Autowired
	private UnidadeService unidadeService;
	
	
	@GetMapping("/nova")
	public ModelAndView nova(MensagemNotificacaoDto mensagem) {
		ModelAndView mv = new ModelAndView("interno/mensagemNotificacao/novo");
		
		mv.addObject("mensagem",mensagem);
		/* List<UnidadeConsulta> listaUnidadesPoliciaisVw() */
		mv.addObject("listaUnidade",unidadeService.pesquisarTodasUnidades());
		mv.addObject("tipoMensagem",tipoMensagemNotificacaoService.pesquisaTodosTipos());
		mv.addObject("idFuncionario",1);
		mv.addObject("listaTipoPesquisaPessoa", TipoPesquisaPessoaFisica.values());

		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@ModelAttribute MensagemNotificacaoDto mensagem /* , HttpSession session */,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/notificacao/nova");
		mensagem.setFuncionarioCriador(1);
		
		HashMap<String, String> resultado = mensagemNotificacaoService.salvar(mensagem);
		
			/*
			 * EquipeBo usuario = (EquipeBo) session.getAttribute("hs_func"); Funcionario
			 * funcionario =
			 * funcionarioService.procuraPorIdFuncionarioVw(usuario.getIdFuncionario());
			 */
		
		 if(Optional.ofNullable(resultado).isPresent()) {
			 for (Map.Entry<String, String> entry : resultado.entrySet()) {
				 redirectAttributes.addFlashAttribute(entry.getKey(),entry.getValue());
			} 
			
		
			 redirectAttributes.addFlashAttribute("mensagem",mensagem);
				

		 }
		return mv; 
	}
	
	
	
	@GetMapping("/index")
	public ModelAndView index(MensagemNotificacaoDto mensagem) {
		ModelAndView mv = new ModelAndView("interno/mensagemNotificacao/index");
		mv.addObject("idFuncionario",1);

		return mv;
	}
	
	@ResponseBody
	@GetMapping("/lista")
	public List<MensagemNotificacaoVisualizarDto> listaNotificacoes(/* , HttpSession session */){
		Funcionario funcionario = new Funcionario();
		funcionario.setIdFuncionario(1);
		Unidade unidade = new Unidade();
		unidade.setId(1);
		funcionario.setUnidade(unidade);
		/*
		 * EquipeBo usuario = (EquipeBo) session.getAttribute("hs_func"); Funcionario
		 * funcionario =
		 * funcionarioService.procuraPorIdFuncionarioVw(usuario.getIdFuncionario());
		 */
		return mensagemNotificacaoService.getMensagensFuncionario(funcionario);
	
	}
	
	
	@ResponseBody
	@GetMapping("/pagina")
	public PaginaNotificacaoDto pagina(@RequestParam(value="page", defaultValue = "0") int pagina,
			@RequestParam(value="tamanho", defaultValue = "10" ) int tamanho/* , HttpSession session */){
		
		Pageable pageable = PageRequest.of(pagina, tamanho,Sort.by("mensagem.tipoMensagem.id","mensagem.dataCriacao").ascending());
		Funcionario funcionario = new Funcionario();
		funcionario.setIdFuncionario(1);
		Unidade unidade = new Unidade();
		unidade.setId(1);
		funcionario.setUnidade(unidade);
		
		/*
		 *  EquipeBo usuario = (EquipeBo) session.getAttribute("hs_func"); Funcionario
		 *  funcionario =
		 *  funcionarioService.procuraPorIdFuncionarioVw(usuario.getIdFuncionario());
		 */
		return mensagemNotificacaoService.getPagina( pageable,funcionario);
	
	}
	
	
	
	@GetMapping("/paginacao")
	public ModelAndView paginacao(
			@RequestParam(value="page", defaultValue = "0") int pagina, 
			@RequestParam(value="tamanho", defaultValue = "10" ) int tamanho/* , HttpSession session */){
		ModelAndView mv = new ModelAndView("interno/mensagemNotificacao/todasNotificacoes");
		Pageable pageable = PageRequest.of(pagina, tamanho,Sort.by("mensagem.tipoMensagem.id","mensagem.dataCriacao").ascending());
		Funcionario funcionario = new Funcionario();
		funcionario.setIdFuncionario(1);
		Unidade unidade = new Unidade();
		unidade.setId(1);
		funcionario.setUnidade(unidade);
		
		/*
		 *  EquipeBo usuario = (EquipeBo) session.getAttribute("hs_func"); Funcionario
		 *  funcionario =
		 *  funcionarioService.procuraPorIdFuncionarioVw(usuario.getIdFuncionario());
		 */
		
		mv.addObject("pagina", mensagemNotificacaoService.getPagina( pageable,funcionario));
		return mv;
	
	}
	
	
	
	@ResponseBody
	@PostMapping("/visualizar/{id}")
	public boolean visualizar(@PathVariable("id") MensagemNotificacao mensagem /* , HttpSession session */) {
		Funcionario funcionario = new Funcionario();
		funcionario.setIdFuncionario(1);
		Unidade unidade = new Unidade();
		unidade.setId(1);
		funcionario.setUnidade(unidade);
		System.err.println("visualizei :D");
		return mensagemNotificacaoService.visualizar(mensagem, funcionario);
	}
	
	
	
}
