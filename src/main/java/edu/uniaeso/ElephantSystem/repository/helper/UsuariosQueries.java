package edu.uniaeso.ElephantSystem.repository.helper;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.repository.filter.UsuarioFilter;

public interface UsuariosQueries {
	
	Optional<Usuario> porEmailEAtivo(String email);
	
	List<String> permissoes(Usuario usuario);
	
	Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
	
	Long total(UsuarioFilter usuarioFilter);
	
	Optional<Usuario> porEmail(String email);
	
}

