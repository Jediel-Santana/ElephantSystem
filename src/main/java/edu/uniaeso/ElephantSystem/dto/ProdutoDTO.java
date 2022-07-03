package edu.uniaeso.ElephantSystem.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import edu.uniaeso.ElephantSystem.modelo.Categoria;
import edu.uniaeso.ElephantSystem.modelo.Cor;
import edu.uniaeso.ElephantSystem.modelo.Genero;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.ProdutoTamanho;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;

public class ProdutoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String descricao;
	
	private String codigoDeBarras;
	
	private Genero genero;

	private Categoria categoria;
	
	private List<Tamanho> tamanhos;
	
	private Cor cor;
	
	private BigDecimal preco;
	
	private String foto;
	
	private List<String> contentType;
	
	private List<String> urlFoto;
	
	
	public ProdutoDTO(Produto p) {
		this.id = p.getId();
		this.descricao = p.getDescricao();
		this.codigoDeBarras = p.getCodigoDeBarras();
		this.genero = p.getGenero();
		this.categoria = p.getCategoria();
		this.tamanhos = p.getProdutoTamanho().stream().map(ProdutoTamanho::getTamanho).collect(Collectors.toList());
		this.preco = p.getPreco();
		this.contentType = p.getContentType();
		this.urlFoto = p.getUrlFoto();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Tamanho> getTamanhos() {
		return tamanhos;
	}

	public void setTamanhos(List<Tamanho> tamanhos) {
		this.tamanhos = tamanhos;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<String> getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(List<String> urlFotos) {
		this.urlFoto = urlFotos;
	}

	public List<String> getContentType() {
		return contentType;
	}

	public void setContentType(List<String> contentType) {
		this.contentType = contentType;
	}
	
	public String getFotoPrincipal() {
		return !this.urlFoto.isEmpty() ? this.urlFoto.get(0) : "";
	}
}
