package br.gov.ma.ssp.model;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

@Table
@Entity
public class MensagemNotificacaoVisualizada implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="fk_funcionario_destinatario")
	private Funcionario funcionarioDestinatario;
	
	@Column(name="data_visualizacao")
	private Date dataVisualizacao;
	
	@OneToOne
	@JoinColumn(name="fk_mensagem")
	private MensagemNotificacao mensagem;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Funcionario getFuncionarioDestinatario() {
		return funcionarioDestinatario;
	}

	public void setFuncionarioDestinatario(Funcionario funcionarioDestinatario) {
		this.funcionarioDestinatario = funcionarioDestinatario;
	}

	public Date getDataVisualizacao() {
		return dataVisualizacao;
	}

	public void setDataVisualizacao(Date dataVisualizacao) {
		this.dataVisualizacao = dataVisualizacao;
	}

	public MensagemNotificacao getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemNotificacao mensagem) {
		this.mensagem = mensagem;
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
		MensagemNotificacaoVisualizada other = (MensagemNotificacaoVisualizada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
