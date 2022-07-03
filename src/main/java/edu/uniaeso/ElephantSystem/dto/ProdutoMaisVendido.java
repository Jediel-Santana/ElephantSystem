package edu.uniaeso.ElephantSystem.dto;

public class ProdutoMaisVendido {
		
	private String descricao;
	private Integer total;
	
	public ProdutoMaisVendido(String descricao, Integer total) {
		this.descricao = descricao;
		this.total = total;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getTotal() {
		return total;
	}

	
}
