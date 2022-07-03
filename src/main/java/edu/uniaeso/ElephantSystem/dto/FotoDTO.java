package edu.uniaeso.ElephantSystem.dto;

public class FotoDTO {
	
	private String nome;
	
	private String contentType;
	
	private String urlFoto;
	
	public FotoDTO() {
	}
	
	public FotoDTO(String nome, String contentType, String urlFoto) {
		this.nome = nome;
		this.contentType = contentType;
		this.urlFoto = urlFoto;
	}

	public String getNome() {
		return nome;
	}

	public String getContentType() {
		return contentType;
	}

	public String getUrlFoto() {
		return urlFoto;
	}
	
}
