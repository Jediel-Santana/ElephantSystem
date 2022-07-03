package edu.uniaeso.ElephantSystem.dto;

import edu.uniaeso.ElephantSystem.modelo.Tamanho;

public class DadosProduto {
	
	private Long idProduto;
	private Tamanho tamanho;
	private Integer quantidade = 1;
	
	public Tamanho getTamanho() {
		return tamanho;
	}
	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	
}
