package edu.uniaeso.ElephantSystem.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "produto_tamanho")
@DynamicUpdate
public class ProdutoTamanho implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto_tamanho")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	@JsonIgnore
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "id_tamanho")
	private Tamanho tamanho;
	
	@Column(name = "quantidade")
	private Integer quantidade;
	
	public ProdutoTamanho() {
	}
	
	public ProdutoTamanho(Tamanho tamanho, Integer quantidade) {
		this.tamanho = tamanho;
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Produto produto) {
		this.produto = produto;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

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
	
	public void darBaixaQuantidade(Integer quantidade) {
		this.quantidade = this.quantidade - quantidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoTamanho other = (ProdutoTamanho) obj;
		return Objects.equals(id, other.id);
	}
	
}
