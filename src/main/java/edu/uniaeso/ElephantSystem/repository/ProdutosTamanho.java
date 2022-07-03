package edu.uniaeso.ElephantSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uniaeso.ElephantSystem.modelo.ProdutoTamanho;
import edu.uniaeso.ElephantSystem.repository.helper.ProdutosTamanhoQueries;

public interface ProdutosTamanho extends JpaRepository<ProdutoTamanho, Long>, ProdutosTamanhoQueries{

}
