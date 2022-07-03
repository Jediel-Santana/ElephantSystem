package edu.uniaeso.ElephantSystem.repository;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.repository.helper.UsuariosQueries;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries{

	
	
	
}
