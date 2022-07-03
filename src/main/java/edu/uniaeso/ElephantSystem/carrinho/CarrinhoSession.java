package edu.uniaeso.ElephantSystem.carrinho;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import edu.uniaeso.ElephantSystem.dto.ItemVendaDTO;
import edu.uniaeso.ElephantSystem.modelo.ItemVendaTamanho;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;

@SessionScope
@Component
public class CarrinhoSession {

	private List<ItemVenda> itens = new ArrayList<>();

	public void adicionarItem(Produto produto, int quantidade, Tamanho tamanho) {
		Optional<ItemVenda> itemVendaBuscado = buscarItemVendaPorProduto(produto);

		if (itemVendaBuscado.isPresent()) {
			ItemVenda itemVenda = itemVendaBuscado.get();
			List<ItemVendaTamanho> itensVendaTamanho = itemVenda.getItensVendaTamanho();
			
			if(itensVendaTamanho.stream().filter(ivt -> ivt.getTamanho().equals(tamanho)).findAny().isPresent()) {
				ItemVendaTamanho itemVendaTamanho = itemVenda.getItemVendaTamanhoPorTamanho(tamanho).get();
				itemVendaTamanho.setQuantidade(itemVendaTamanho.getQuantidade() + quantidade);
			} else {
				itemVenda.adicionarItemVendaTamanho(tamanho, quantidade);
			}
			
		}  else {
			ItemVenda itemVenda = new ItemVenda(produto);
			itemVenda.adicionarItemVendaTamanho(tamanho, quantidade);
			itens.add(itemVenda);
		}
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public List<ItemVendaDTO> getItensVendaDTO() {
		return ItemVendaDTO.getItensVendaDTO(itens);
	}

	public ItemVendaDTO alterarTamanhoItem(Produto produto, Tamanho tamanhoAtual, Tamanho tamanhoNovo) {
		Optional<ItemVenda> itemVenda = buscarItemVendaPorProduto(produto);
		
		if (itemVenda.isPresent()) {
			Optional<ItemVendaTamanho> itemVendaTamanhoBuscado = itemVenda.get().getItensVendaTamanho().stream()
					.filter(ivt -> ivt.getTamanho().getId().equals(tamanhoNovo.getId())).findAny();
			if (itemVendaTamanhoBuscado.isPresent()) {
				ItemVendaTamanho ivt = itemVendaTamanhoBuscado.get();
				Integer quantidadeAtual = ivt.getQuantidade();
				ivt.setQuantidade(quantidadeAtual + 1);
			} else {
				itemVenda.get().adicionarItemVendaTamanho(tamanhoNovo, 1);
			}
			
			return new ItemVendaDTO(itemVenda.get(), tamanhoNovo, itemVenda.get().getItemVendaTamanhoPorTamanho(tamanhoNovo).get().getQuantidade());
		}
		
		return null;
	}

	public ItemVendaDTO alterarQuantidadeItem(Produto produto, Tamanho tamanho, Integer quantidade) {
		Optional<ItemVenda> itemVenda = buscarItemVendaPorProduto(produto);
		if (itemVenda.isPresent()) {
			ItemVendaTamanho itemVendaTamanhoAlterado = itemVenda.get().adicionarItemVendaTamanho(tamanho, quantidade);
			return new ItemVendaDTO(itemVenda.get(), tamanho, itemVendaTamanhoAlterado.getQuantidade());
		} 
		
		return null;
	}
	
	public BigDecimal getValorTotalVenda() {
		return this.itens.stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}
	
	private Optional<ItemVenda> buscarItemVendaPorProduto(Produto produto) {
		return itens.stream().filter(iv -> iv.getProduto().getId().equals(produto.getId())).findAny();
	}

	public void resetar() {
		this.itens = new ArrayList<>();
	}

	public void removeItem(Long idProduto, Long idTamanho) {
		Optional<ItemVenda> itemVendaOptional = this.itens.stream().filter(iv -> iv.getProduto().getId().equals(idProduto)).findFirst();
		
		if(itemVendaOptional.isPresent()) {
			ItemVenda itemVenda = itemVendaOptional.get();
			itemVenda.removeItemVendaTamanho(idTamanho);
			if(itemVenda.getItensVendaTamanho().isEmpty()) {
				this.itens.removeIf(iv -> iv.getProduto().getId().equals(idProduto));
			}
		}
		
		//		this.itens.stream().forEach(iv -> {
//			if(iv.getProduto().getId().equals(idProduto)) {
//				iv.removeItemVendaTamanho(idTamanho);
//				if(iv.getItensVendaTamanho().isEmpty()) {
//					this.itens.remove(iv);
//					break;
//				}
//			}
//		});
		
	}
}
