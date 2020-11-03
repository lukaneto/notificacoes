package br.gov.ma.ssp.model.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MensagemNotificacaoVisualizarDto {
	private Integer Id;
	private String descricao;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dataCriacao;
	
	private Date dataValidadeInicio;
	private Date dataValidadeFim;
	
	private TipoNotificacaoDto tipoMensagem;
	private Boolean ehUnidade;
	private Boolean addUnidadeFilha;
	private Boolean ehFuncionario;
	private Boolean restricaoForca;
	private Boolean temValidade;
	private Boolean ativo;
	private String titulo;
	private boolean visualizado;
	
	private List<MensagemLinkDto> listaLink;
	private List<MensagemImagemDto> listaImagem;
	
	private Integer unidadeDestinatario;
	private List<Integer> funcionarioDestinatario;

	
	public Boolean getVisualizado() {
		return visualizado;
	}
	public void setVisualizado(boolean visualizado) {
		this.visualizado = visualizado;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<MensagemLinkDto> getListaLink() {
		return listaLink;
	}
	public void setListaLink(List<MensagemLinkDto> listaLink) {
		this.listaLink = listaLink;
	}
	public List<MensagemImagemDto> getListaImagem() {
		return listaImagem;
	}
	public void setListaImagem(List<MensagemImagemDto> listaImagem) {
		this.listaImagem = listaImagem;
	}
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
	public TipoNotificacaoDto getTipoMensagem() {
		return tipoMensagem;
	}
	public void setTipoMensagem(TipoNotificacaoDto tipoMensagem) {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MensagemNotificacaoVisualizarDto other = (MensagemNotificacaoVisualizarDto) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}
	
	
}
