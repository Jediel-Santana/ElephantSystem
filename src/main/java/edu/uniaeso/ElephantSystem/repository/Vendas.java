package edu.uniaeso.ElephantSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.helper.VendasQueries;

@Repository
public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries{



}
