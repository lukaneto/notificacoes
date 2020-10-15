package br.gov.ma.ssp.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.ma.ssp.model.TipoMensagemNotificacao;
import br.gov.ma.ssp.model.dto.MensagemNotificacaoDto;
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
	
	
	
	
	
}
