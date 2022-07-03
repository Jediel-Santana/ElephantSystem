package edu.uniaeso.ElephantSystem.repository.helper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.uniaeso.ElephantSystem.dto.ProdutoDTO;
import edu.uniaeso.ElephantSystem.dto.ProdutoMaisVendido;
import edu.uniaeso.ElephantSystem.dto.ValorItensEstoque;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.repository.filter.ProdutoFilter;

public interface ProdutosQueries {
	
	List<Produto> maisRecentesDosUltimos30Dias();
	
	Page<ProdutoDTO> filtrar(ProdutoFilter produtoFilter, Pageable pageable);
	
	Long total(ProdutoFilter produtoFilter);
	
	ValorItensEstoque valorItensEstoque();
	
	List<ProdutoMaisVendido> maisVendidos();
}
