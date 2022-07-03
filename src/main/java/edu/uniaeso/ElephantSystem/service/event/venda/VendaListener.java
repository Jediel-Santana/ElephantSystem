package edu.uniaeso.ElephantSystem.service.event.venda;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edu.uniaeso.ElephantSystem.carrinho.ItemVenda;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.Produtos;

@Component
public class VendaListener {
	
	@Autowired
	private Produtos produtos;
	
	@Transactional
	@EventListener
	public void VendaEmitida(VendaEvent vendaEvent) {
		Venda venda = vendaEvent.getVenda();
		for (ItemVenda itemVenda : venda.getItensVenda()) {
			Produto produto = itemVenda.getProduto();
			itemVenda.getItensVendaTamanho().stream().forEach(ivt -> produto.darBaixaProdutoTamanho(ivt.getTamanho(), ivt.getQuantidade()));
			produtos.save(produto);
		}
		
	}
	
	
}
