package edu.uniaeso.ElephantSystem.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.uniaeso.ElephantSystem.modelo.converter.StringListConverter;
import edu.uniaeso.ElephantSystem.repository.listener.ProdutoEntityListener;

@EntityListeners(ProdutoEntityListener.class)
@Entity
@Table(name = "produto")
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "A descrição é obrigatória")
	private String descricao;
	
	@NotBlank(message = "O código de barras é obrigatório")
	@Column(name = "codigo_de_barras")
	private String codigoDeBarras;
	
	@NotNull(message = "O gênero é obrigatório")
	@Enumerated(EnumType.STRING)
	private Genero genero;

	@NotNull(message = "A categoria é obrigatória")
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	@Size(min = 1, message = "Insira pelo menos um tamanho")
	@OneToMany(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProdutoTamanho> produtoTamanhos = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private Cor cor;
	
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@NotNull(message = "O preço é obrigatório")
	private BigDecimal preco;
	
	@Size(min = 1, message = "Insira pelo menos uma foto")
	@Convert(converter = StringListConverter.class)
	private List<String> foto = new ArrayList<>();
	
	@Column(name = "content_type")
	@Convert(converter = StringListConverter.class)
	private List<String> contentType = new ArrayList<>();
	
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@Transient
	private boolean novaFoto;
	
	@Transient
	private List<String> urlFoto = new ArrayList<>();
	
	@Transient
	private List<String> fotosRemovidas = new ArrayList<>();
	
	public Produto() {
	}

	public Produto(String descricao, String codigoDeBarras, Categoria categoria, List<ProdutoTamanho> tamanho,
			Genero modelo, Cor cor, BigDecimal preco) {
		this.descricao = descricao;
		this.codigoDeBarras = codigoDeBarras;
		this.categoria = categoria;
		this.produtoTamanhos = tamanho;
		this.genero = modelo;
		this.cor = cor;
		this.preco = preco;
		this.setTotalEstoque(getTotalEstoque());
	}
	
	
	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public List<ProdutoTamanho> getProdutoTamanho() {
		return produtoTamanhos;
	}

	public void setProdutoTamanho(List<ProdutoTamanho> produtoTamanho) {
		this.produtoTamanhos = produtoTamanho;
	}

	public Genero getModelo() {
		return genero;
	}

	public void setModelo(Genero modelo) {
		this.genero = modelo;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Integer getTotalEstoque() {
		return this.produtoTamanhos.stream().mapToInt(ProdutoTamanho::getQuantidade).sum();
	}
	
	public void setTotalEstoque(Integer totalEstoque) {
		this.quantidadeEstoque = totalEstoque;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public List<String> getFoto() {
		return foto;
	}

	public void setFoto(List<String> foto) {
		this.foto = foto;
	}

	public List<String> getContentType() {
		return contentType;
	}

	public void setContentType(List<String> contentType) {
		this.contentType = contentType;
	}

	public boolean isNovaFoto() {
		return novaFoto;
	}

	public void setNovaFoto(boolean novaFoto) {
		this.novaFoto = novaFoto;
	}
	
	public List<String> getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(List<String> urlFoto) {
		this.urlFoto = urlFoto;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public List<String> getFotosOuMock() {
//		return  !StringUtils.isEmpty(foto) ? foto : "mock-produto.jpg";
		return null;
	}
	
	public List<Tamanho> getTamanhosDisponiveis() {
		return produtoTamanhos.stream().map(ProdutoTamanho::getTamanho).collect(Collectors.toList());
	}
	
	public void adicionarProdutoTamanho(Tamanho tamanho, Integer quantidade) {
		Optional<ProdutoTamanho> tamanhoBuscado = buscarProdutoTamanhoPorTamanho(tamanho);
		if(tamanhoBuscado.isPresent()) {
			ProdutoTamanho produtoTamanho = tamanhoBuscado.get();
			produtoTamanho.setQuantidade(quantidade);
		} else {
			ProdutoTamanho produtoTamanho = new ProdutoTamanho(tamanho, quantidade);
			produtoTamanho.setProduto(this);
			this.produtoTamanhos.add(produtoTamanho);
		}
	}
	
	public Optional<ProdutoTamanho> buscarProdutoTamanhoPorTamanho(Tamanho tamanho) {
		return this.produtoTamanhos.stream().filter(pt -> pt.getTamanho().getId().equals(tamanho.getId())).findFirst();
	}
	
	public void darBaixaProdutoTamanho(Tamanho tamanho, Integer quantidade) {
		Optional<ProdutoTamanho> produtoTamanho = this.produtoTamanhos.stream().filter(t -> t.getTamanho().equals(tamanho)).findFirst();
		produtoTamanho.ifPresent(pt -> pt.darBaixaQuantidade(quantidade));
		this.quantidadeEstoque = this.quantidadeEstoque - quantidade;
	}
	
	public void adicionarFoto(String nomeFoto, String contentType, String urlFoto) {
		this.foto.add(nomeFoto);
		this.contentType.add(contentType);
		this.urlFoto.add(urlFoto);
	}
	
	public void removerFoto(int index) {
		String nome = this.foto.get(index);
		this.fotosRemovidas.add(nome);
		this.foto.remove(index);
		this.contentType.remove(index);
		this.urlFoto.remove(index);
	}
	
	public List<ProdutoTamanho> getProdutoTamanhos() {
		return produtoTamanhos;
	}
	
	public List<Tamanho> getTamanhosCadastrados() {
		return this.produtoTamanhos.stream().map(ProdutoTamanho::getTamanho).collect(Collectors.toList());
	}
	
	public boolean isNovo() {
		return this.id == null;
	}

	public void removeTamanhoProduto(Tamanho tamanho) {
		this.produtoTamanhos.removeIf(pt -> pt.getTamanho().getId().equals(tamanho.getId()));
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
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

	public boolean temFoto() {
		return !this.foto.isEmpty();
	}


	


}
