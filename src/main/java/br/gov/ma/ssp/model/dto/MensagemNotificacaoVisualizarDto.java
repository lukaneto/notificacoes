package br.gov.ma.ssp.model.dto;

import java.util.Date;
import java.util.List;

public class MensagemNotificacaoVisualizarDto {
	private Integer Id;
	private String descricao;
	private Date dataCriacao;
	private Date dataValidadeInicio;
	private Date dataValidadeFim;
	private Integer tipoMensagem;
	private Boolean ehUnidade;
	private Boolean addUnidadeFilha;
	private Boolean ehFuncionario;
	private Boolean restricaoForca;
	private Boolean temValidade;
	private Boolean ativo;
	
	private List<MensagemLinkDto> listaLink;
	
	private Integer unidadeDestinatario;
	private List<Integer> funcionarioDestinatario;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataValidadeInicio() {
		return dataValidadeInicio;
	}
	public void setDataValidadeInicio(Date dataValidadeInicio) {
		this.dataValidadeInicio = dataValidadeInicio;
	}
	public Date getDataValidadeFim() {
		return dataValidadeFim;
	}
	public void setDataValidadeFim(Date dataValidadeFim) {
		this.dataValidadeFim = dataValidadeFim;
	}
	public Integer getTipoMensagem() {
		return tipoMensagem;
	}
	public void setTipoMensagem(Integer tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}
	public Boolean getEhUnidade() {
		return ehUnidade;
	}
	public void setEhUnidade(Boolean ehUnidade) {
		this.ehUnidade = ehUnidade;
	}
	public Boolean getAddUnidadeFilha() {
		return addUnidadeFilha;
	}
	public void setAddUnidadeFilha(Boolean addUnidadeFilha) {
		this.addUnidadeFilha = addUnidadeFilha;
	}
	public Boolean getEhFuncionario() {
		return ehFuncionario;
	}
	public void setEhFuncionario(Boolean ehFuncionario) {
		this.ehFuncionario = ehFuncionario;
	}
	public Boolean getRestricaoForca() {
		return restricaoForca;
	}
	public void setRestricaoForca(Boolean restricaoForca) {
		this.restricaoForca = restricaoForca;
	}
	public Boolean getTemValidade() {
		return temValidade;
	}
	public void setTemValidade(Boolean temValidade) {
		this.temValidade = temValidade;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Integer getUnidadeDestinatario() {
		return unidadeDestinatario;
	}
	public void setUnidadeDestinatario(Integer unidadeDestinatario) {
		this.unidadeDestinatario = unidadeDestinatario;
	}
	public List<Integer> getFuncionarioDestinatario() {
		return funcionarioDestinatario;
	}
	public void setFuncionarioDestinatario(List<Integer> funcionarioDestinatario) {
		this.funcionarioDestinatario = funcionarioDestinatario;
	}
	
	
}
