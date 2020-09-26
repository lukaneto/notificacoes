package br.gov.ma.ssp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Table
@Entity
public class MensagemNotificacaoImagem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_notificacao_imagem")
	private Integer id;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="fk_funcionario_criador")
	private Funcionario funcionarioCriador;
	
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	@ManyToOne
	@JoinColumn(name="fk_mensagem")
	private MensagemNotificacao mensagem;
	
	private String imagem;
	
	private Boolean ativo;

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

	public MensagemNotificacao getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemNotificacao mensagem) {
		this.mensagem = mensagem;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
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
		MensagemNotificacaoImagem other = (MensagemNotificacaoImagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
