package edu.uniaeso.ElephantSystem.repository.filter;

import java.math.BigDecimal;
import java.util.List;

import edu.uniaeso.ElephantSystem.modelo.Categoria;
import edu.uniaeso.ElephantSystem.modelo.Cor;
import edu.uniaeso.ElephantSystem.modelo.Genero;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;

public class ProdutoFilter {
	
	private String descricao;
	
	private String codigoDeBarras;
	
	private List<Genero> generos;

	private List<Categoria> categorias;
	
	private List<Tamanho> tamanhos;
	
	private List<Cor> cores;
	
	private BigDecimal valorDe;
	
	private BigDecimal valorAte;
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Tamanho> getTamanhos() {
		return tamanhos;
	}

	public void setTamanhos(List<Tamanho> tamanhos) {
		this.tamanhos = tamanhos;
	}

	public List<Cor> getCores() {
		return cores;
	}

	public void setCores(List<Cor> cores) {
		this.cores = cores;
	}

	public BigDecimal getValorDe() {
		return valorDe;
	}

	public void setValorDe(BigDecimal valorDe) {
		this.valorDe = valorDe;
	}

	public BigDecimal getValorAte() {
		return valorAte;
	}

	public void setValorAte(BigDecimal valorAte) {
		this.valorAte = valorAte;
	}



}
