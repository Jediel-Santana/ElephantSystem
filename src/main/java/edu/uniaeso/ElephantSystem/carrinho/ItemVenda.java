package edu.uniaeso.ElephantSystem.carrinho;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import edu.uniaeso.ElephantSystem.modelo.ItemVendaTamanho;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.ProdutoTamanho;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;
import edu.uniaeso.ElephantSystem.modelo.Venda;

@Entity
@Table(name = "item_venda")
public class ItemVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	@OneToMany(mappedBy = "itemVenda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVendaTamanho> itensVendaTamanho = new ArrayList<>();

	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario;

	@ManyToOne
	@JoinColumn(name = "id_venda")
	private Venda venda;

	public ItemVenda(Produto produto, int quantidade, Tamanho tamanho) {
		setProduto(produto);
	}

	public ItemVenda() {
	}

	public ItemVenda(Produto produto) {
		setProduto(produto);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.precoUnitario = produto.getPreco();
		this.produto = produto;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getValorTotal() {
		Integer quantidadeTotal = this.itensVendaTamanho.stream().map(ItemVendaTamanho::getQuantidade)
				.reduce(Integer::sum).orElse(Integer.valueOf(0));
		return precoUnitario.multiply(BigDecimal.valueOf(quantidadeTotal));
	}

	public List<ItemVendaTamanho> getItensVendaTamanho() {
		return itensVendaTamanho;
	}

	public void setItensVendaTamanho(List<ItemVendaTamanho> itensVendaTamanho) {
		this.itensVendaTamanho = itensVendaTamanho;
	}

	public ItemVendaTamanho adicionarItemVendaTamanho(Tamanho tamanho, int quantidade) {
		Optional<ItemVendaTamanho> itemVendaTamanho = getItemVendaTamanhoPorTamanho(tamanho);

		if (itemVendaTamanho.isPresent()) {
			ItemVendaTamanho ivt = itemVendaTamanho.get();
			ivt.setQuantidade(quantidade);
			return ivt;
		} else {
			ItemVendaTamanho itemVendaTamanhoNovo = new ItemVendaTamanho();
			itemVendaTamanhoNovo.setQuantidade(quantidade);
			itemVendaTamanhoNovo.setItemVenda(this);
			itemVendaTamanhoNovo.setTamanho(tamanho);
			itensVendaTamanho.add(itemVendaTamanhoNovo);
			return itemVendaTamanhoNovo;
		}
	}
	
	public void removeItemVendaTamanho(Long tamanho) {
		this.itensVendaTamanho.removeIf(ivt -> ivt.getTamanho().getId().equals(tamanho));
	}

	public Optional<ItemVendaTamanho> getItemVendaTamanhoPorTamanho(Tamanho tamanho) {
		return itensVendaTamanho.stream().filter(ivt -> ivt.getTamanho().equals(tamanho)).findAny();
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;

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
		ItemVenda other = (ItemVenda) obj;
		return Objects.equals(id, other.id);
	}
	
}
