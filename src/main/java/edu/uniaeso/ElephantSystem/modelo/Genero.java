package edu.uniaeso.ElephantSystem.modelo;

public enum Genero {
	
	MASCULINO("Masculino"), 
	FEMININO("Feminino"), 
	INFANTIL("Infantil"), 
	UNISSEX("Unissex");
	
	private String descricao;
	
	Genero(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
