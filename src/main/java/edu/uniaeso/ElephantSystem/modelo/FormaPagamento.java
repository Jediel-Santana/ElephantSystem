package edu.uniaeso.ElephantSystem.modelo;

public enum FormaPagamento {
	
	BOLETO("BOLETO"),
	CREDITO("CRÉDITO"),
	DEBITO("DÉBITO");
	
	private String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	
}
