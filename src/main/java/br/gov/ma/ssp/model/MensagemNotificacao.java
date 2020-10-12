package br.gov.ma.ssp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table
@Entity
public class MensagemNotificacao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String titulo;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="fk_funcionario_criador")
	private Funcionario funcionarioCriador;
	
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	@Column(name="data_validade_inicio")
	private Date dataValidadeInicio;
	
	@Column(name="data_validade_fim")
	private Date dataValidadeFim;
	
	@ManyToOne
	@JoinColumn(name="fk_tipo_mensagem")
	private TipoMensagemNotificacao tipoMensagem;
	
	@Column(name="eh_unidade")
	private Boolean ehUnidade;
	
	@Column(name="add_unid_filha")
	private Boolean addUnidFilha;
	
	@Column(name="eh_funcionario")
	private Boolean ehFuncionario;
	
	@Column(name="restricao_forca")
	private Boolean restricaoForca;
	
	@Column(name="tem_validade")
	private Boolean temValidade;
	
	private Boolean ativo;
	
	
	
	@OneToMany(mappedBy = "mensagem",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<MensagemNotificacaoLink> listaLink;
	
	@OneToMany(mappedBy = "mensagem",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<MensagemNotificacaoImagem> listaImagem;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Funcionario getFuncionarioCriador() {
		return funcionarioCriador;
	}

	public void setFuncionarioCriador(Funcionario funcionarioCriador) {
		this.funcionarioCriador = funcionarioCriador;
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

	public TipoMensagemNotificacao getTipoMensagem() {
		return tipoMensagem;
	}

	public void setTipoMensagem(TipoMensagemNotificacao tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}

	public Boolean getEhUnidade() {
		return ehUnidade;
	}

	public void setEhUnidade(Boolean ehUnidade) {
		this.ehUnidade = ehUnidade;
	}

	public Boolean getAddUnidFilha() {
		return addUnidFilha;
	}

	public void setAddUnidFilha(Boolean addUnidFilha) {
		this.addUnidFilha = addUnidFilha;
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

	
	
	
	public List<MensagemNotificacaoLink> getListaLink() {
		return listaLink;
	}

	public void setListaLink(List<MensagemNotificacaoLink> listaLink) {
		this.listaLink = listaLink;
	}

	public List<MensagemNotificacaoImagem> getListaImagem() {
		return listaImagem;
	}

	public void setListaImagem(List<MensagemNotificacaoImagem> listaImagem) {
		this.listaImagem = listaImagem;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MensagemNotificacao other = (MensagemNotificacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}
