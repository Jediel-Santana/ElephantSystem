package edu.uniaeso.ElephantSystem.modelo;

import java.io.Serializable;

public enum Categoria implements Serializable {
	
	CAMISA("Camisas"), 
	CAMISETA("Camisetas"),
	REGATA("Regatas"),
	BLUSA("Blusas"), 
	BERMUDA("Bermudas"), 
	VESTIDO("Vestidos"), 
	CALCA("Calças"),
	SHORT("Short");
	
	private String descricao;

	Categoria(String descricao) {
		this.descricao = descricao;
		
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
