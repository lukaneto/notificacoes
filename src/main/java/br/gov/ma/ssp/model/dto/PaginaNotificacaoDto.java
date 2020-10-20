package br.gov.ma.ssp.model.dto;

import java.util.List;

public class PaginaNotificacaoDto {

	
	private Integer quantidadeTotalNotificacao;
	private Integer quantidadeNaoVisualizada;
	private List<MensagemNotificacaoVisualizarDto> notificacao;
	private List<MensagemNotificacaoVisualizarDto> notificacaoNaoVisualizada;
	
	private Integer pagina;
	private Integer maxPagina;
	
	
	
	public List<MensagemNotificacaoVisualizarDto> getNotificacaoNaoVisualizada() {
		return notificacaoNaoVisualizada;
	}
	public void setNotificacaoNaoVisualizada(List<MensagemNotificacaoVisualizarDto> notificacaoNaoVisualizada) {
		this.notificacaoNaoVisualizada = notificacaoNaoVisualizada;
	}
	public Integer getQuantidadeTotalNotificacao() {
		return quantidadeTotalNotificacao;
	}
	public void setQuantidadeTotalNotificacao(Integer quantidadeTotalNotificacao) {
		this.quantidadeTotalNotificacao = quantidadeTotalNotificacao;
	}
	public Integer getQuantidadeNaoVisualizada() {
		return quantidadeNaoVisualizada;
	}
	public void setQuantidadeNaoVisualizada(Integer quantidadeNaoVisualizada) {
		this.quantidadeNaoVisualizada = quantidadeNaoVisualizada;
	}
	public List<MensagemNotificacaoVisualizarDto> getNotificacao() {
		return notificacao;
	}
	public void setNotificacao(List<MensagemNotificacaoVisualizarDto> notificacao) {
		this.notificacao = notificacao;
	}
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public Integer getMaxPagina() {
		return maxPagina;
	}
	public void setMaxPagina(Integer maxPagina) {
		this.maxPagina = maxPagina;
	}
	
	
	
	
	
}
