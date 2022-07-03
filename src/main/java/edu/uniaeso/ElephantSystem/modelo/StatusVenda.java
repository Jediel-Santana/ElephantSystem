package edu.uniaeso.ElephantSystem.modelo;

public enum StatusVenda {
	ANALISE("ANÁLISE"),
	APROVADA("APROVADA"),
	DESPACHADO("DESPACHADO"),
	ENTREGUE("ENTREGUE"),
	CANCELADA("CANCELADA");
	
	private String descricao;
	
	StatusVenda(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
