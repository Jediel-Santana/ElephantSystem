package edu.uniaeso.ElephantSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import edu.uniaeso.ElephantSystem.modelo.Tamanho;


@Repository
public interface Tamanhos extends JpaRepository<Tamanho, Long> {

}
