package br.gov.ma.ssp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.gov.ma.ssp.service.MensagemNotificacaoService;
import br.gov.ma.ssp.service.TipoMensagemNotificacaoService;
import br.gov.ma.ssp.service.UnidadeService;


@Controller
@RequestMapping("/finger")
public class FingerController {

	@Autowired
	private MensagemNotificacaoService mensagemNotificacaoService;
	
	@Autowired
	private TipoMensagemNotificacaoService tipoMensagemNotificacaoService;
	
	@Autowired
	private UnidadeService unidadeService;
	
	
	@GetMapping("/novo/{id}")
	@ResponseBody
	public String nova( @PathVariable("id") String id) {
		System.out.println("Biometria de  "+ id +" Cadastrada com Sucesso!!" );
		
		return "Sucesso!";
	}
	
	
	
}
