package br.gov.ma.ssp.model.enumeration;

public enum TipoPesquisaPessoaFisica {

	
	
	NO("Nome"),
	CPF("CPF"),
	NE("Nome Exato"),
	MM("Nome mãe"),
	MA("Matrícula");
//	RG("RG");
	
	
	private String descricao;

	
	private TipoPesquisaPessoaFisica(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
		
	
}