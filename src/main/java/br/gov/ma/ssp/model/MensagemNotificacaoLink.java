package br.gov.ma.ssp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
public class MensagemNotificacaoLink implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_msg_link")
	private Integer id;
	
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name="fk_funcionario_criador")
	private Funcionario funcionarioCriador;
	@Column(name="data_criacao")
	private Date dataCricao;
	
	@ManyToOne
	@JoinColumn(name="fk_mensagem")
	private MensagemNotificacao mensagem;
	
	private String link;
	
	private Boolean ativo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Funcionario getFuncionarioCriador() {
		return funcionarioCriador;
	}

	public void setFuncionarioCriador(Funcionario funcionarioCriador) {
		this.funcionarioCriador = funcionarioCriador;
	}

	public Date getDataCricao() {
		return dataCricao;
	}

	public void setDataCricao(Date dataCricao) {
		this.dataCricao = dataCricao;
	}

	public MensagemNotificacao getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemNotificacao mensagem) {
		this.mensagem = mensagem;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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
		MensagemNotificacaoLink other = (MensagemNotificacaoLink) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
