package edu.uniaeso.ElephantSystem.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.uniaeso.ElephantSystem.carrinho.ItemVenda;
import edu.uniaeso.ElephantSystem.modelo.ItemVendaTamanho;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;

public class ItemVendaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long idProduto;
	
	private String descricaoProduto;
	
	private BigDecimal precoUnitario;
	
	private Integer quantidade;
	
	private List<Tamanho> tamanhosDisponiveis;
	
	private Tamanho tamanho;
	
	private String fotoThumbnail;
	
	private String nomeFoto;
	
	private String contentType;
	
	private BigDecimal valorTotal;

	private boolean temFoto;
	

	public ItemVendaDTO(ItemVenda iv, Tamanho tamanho, Integer quantidade) {
		this.id = iv.getId();
		this.idProduto = iv.getProduto().getId();
		this.descricaoProduto = iv.getProduto().getDescricao();
		this.precoUnitario = iv.getPrecoUnitario();
		this.quantidade = quantidade;
		this.tamanhosDisponiveis = iv.getProduto().getTamanhosDisponiveis();
		this.tamanho = tamanho;
		this.fotoThumbnail = !iv.getProduto().getUrlFoto().isEmpty() ? iv.getProduto().getUrlFoto().get(0) : "mock-produto.jpg";
		this.nomeFoto = !iv.getProduto().getFoto().isEmpty() ? iv.getProduto().getFoto().get(0) : "mock-produto.jpg";
		this.contentType = !iv.getProduto().getContentType().isEmpty() ? iv.getProduto().getContentType().get(0) : "image/jpeg";
		this.valorTotal = quantidade != null ? precoUnitario.multiply(BigDecimal.valueOf(quantidade)) : precoUnitario;
		this.temFoto = !iv.getProduto().getFoto().isEmpty();
	}

	public ItemVendaDTO() {
		// TODO Auto-generated constructor stub
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.descricaoProduto = nomeProduto;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public String getNomeProduto() {
		return descricaoProduto;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public List<Tamanho> getTamanhosDisponiveis() {
		return tamanhosDisponiveis;
	}

	public void setTamanhosDisponiveis(List<Tamanho> tamanhosDisponiveis) {
		this.tamanhosDisponiveis = tamanhosDisponiveis;
	}

	public String getFotoThumbnail() {
		return fotoThumbnail;
	}

	public void setFotoThumbnail(String fotoThumbnail) {
		this.fotoThumbnail = fotoThumbnail;
	}

	public String getContentType() {
		return contentType;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}
	
	public boolean temFoto() {
		return temFoto;
	}
	
	public String getNomeFoto() {
		return nomeFoto;
	}

	public static List<ItemVendaDTO> getItensVendaDTO(List<ItemVenda> itens) {
		List<ItemVendaDTO> itensVendaDTO = new ArrayList<>();
		for (ItemVenda itemVenda : itens) {
			if (!itemVenda.getItensVendaTamanho().isEmpty()) {
				Map<Tamanho, Integer> tamanhoEQuantidade = itemVenda.getItensVendaTamanho().stream()
						.collect(Collectors.toMap(ItemVendaTamanho::getTamanho, ItemVendaTamanho::getQuantidade));
				itensVendaDTO.addAll(tamanhoEQuantidade.entrySet().stream()
						.map(entry -> new ItemVendaDTO(itemVenda, entry.getKey(), entry.getValue())).collect(Collectors.toList()));
			} else {
				itensVendaDTO.add(new ItemVendaDTO(itemVenda, null, Integer.valueOf(1)));
			}
		}
		return itensVendaDTO;
	}
	
}
