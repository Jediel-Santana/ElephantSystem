package edu.uniaeso.ElephantSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.repository.filter.ProdutoFilter;
import edu.uniaeso.ElephantSystem.repository.helper.ProdutosQueries;

@Repository
public interface Produtos extends JpaRepository<Produto, Long>, ProdutosQueries {

	

}
