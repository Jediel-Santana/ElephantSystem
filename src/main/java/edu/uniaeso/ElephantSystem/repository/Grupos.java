package edu.uniaeso.ElephantSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uniaeso.ElephantSystem.modelo.Grupo;
import edu.uniaeso.ElephantSystem.repository.helper.GruposQueries;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long>, GruposQueries{

}
