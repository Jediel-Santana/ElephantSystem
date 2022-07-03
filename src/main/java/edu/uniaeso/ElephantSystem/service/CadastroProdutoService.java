package edu.uniaeso.ElephantSystem.service;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.ProdutoTamanho;
import edu.uniaeso.ElephantSystem.repository.Produtos;

@Service
public class CadastroProdutoService {
	
	@Autowired
	private Produtos produtos;
	
	@Transactional
	public void salvar(Produto produto) {
		if(produto.isNovo()) {
			produto.setDataCriacao(LocalDateTime.now());
		}
		
		produto.getProdutoTamanhos().stream().forEach(pt -> {
			if(Objects.isNull(pt.getProduto())) {
				pt.setProduto(produto);
			}
		});
		
		produto.setTotalEstoque(produto.getProdutoTamanho().stream().mapToInt(ProdutoTamanho::getQuantidade).sum());
		
		produtos.save(produto);
	}
	
	
}
