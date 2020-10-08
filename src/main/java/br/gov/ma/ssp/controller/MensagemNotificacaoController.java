package br.gov.ma.ssp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.gov.ma.ssp.model.dto.MensagemNotificacaoDto;
import br.gov.ma.ssp.service.MensagemNotificacaoService;

@Controller
@RequestMapping("/notificacao")
public class MensagemNotificacaoController {

	@Autowired
	private MensagemNotificacaoService mensagemNotificacaoService;
	
	@GetMapping("/nova")
	public ModelAndView nova() {
		ModelAndView mv = new ModelAndView("interno/mensagemNotificacao/novo");
		mv.addObject("mensagem",new MensagemNotificacaoDto());
		mv.addObject("idFuncionario",1);
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@ModelAttribute MensagemNotificacaoDto mensagem) {
		ModelAndView mv = new ModelAndView("interno/mensagemNotificacao/novo");
		 HashMap<String, String> resultado = mensagemNotificacaoService.salvar(mensagem);
		 if(Optional.ofNullable(resultado).isPresent()) {
			 for (Map.Entry<String, String> entry : resultado.entrySet()) {
					mv.addObject(entry.getKey(),entry.getValue());
			} 
		 }
		return mv; 
	}
	
	
	
	
	
}
